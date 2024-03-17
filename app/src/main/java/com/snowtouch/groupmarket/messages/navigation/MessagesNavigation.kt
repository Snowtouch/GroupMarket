package com.snowtouch.groupmarket.messages.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.snowtouch.groupmarket.messages.presentation.conversation.ConversationScreen
import com.snowtouch.groupmarket.messages.presentation.conversation.ConversationScreenViewModel
import com.snowtouch.groupmarket.messages.presentation.messages.MessagesScreen
import com.snowtouch.groupmarket.messages.presentation.messages.MessagesScreenViewModel
import org.koin.androidx.compose.koinViewModel

sealed class MessagesRoutes(val route: String) {
    data object Messages: MessagesRoutes("Messages")
    data object Conversation: MessagesRoutes("conversation")
}

fun NavGraphBuilder.messagesFeature(
    navController : NavHostController
) {
    navigation(startDestination = MessagesRoutes.Messages.route, route = "messages_feature") {
        composable(route = MessagesRoutes.Messages.route) {

            val viewModel: MessagesScreenViewModel = koinViewModel()

            MessagesScreen(
                viewModel = viewModel,
                onNavigateToConversation = { conversationId ->
                    navController.navigateToConversation(conversationId)
                }
            )
        }

        composable(
            route = MessagesRoutes.Conversation.route + "/{conversationId}",
            arguments = listOf(
                navArgument(name = "conversationId") {
                    type = NavType.StringType
                }
            )
        ) {
            val conversationId = it.arguments?.getString("conversationId") ?: ""

            val viewModel: ConversationScreenViewModel = koinViewModel()

            ConversationScreen(
                conversationId = conversationId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

fun NavController.navigateToConversation(conversationId: String) {
    this.navigate(route = MessagesRoutes.Conversation.route + "/$conversationId")
}