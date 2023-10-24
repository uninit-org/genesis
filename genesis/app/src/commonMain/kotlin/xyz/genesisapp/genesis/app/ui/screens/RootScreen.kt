package xyz.genesisapp.genesis.app.ui.screens

import xyz.genesisapp.genesis.app.ui.screens.auth.LoginScreen

class RootScreen: GenericLoadingScreen(loadingText = "Welcome to Genesis", { LoginScreen() })  {

}