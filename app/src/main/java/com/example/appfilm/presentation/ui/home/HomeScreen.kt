package com.example.appfilm.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appfilm.presentation.ui.home.DrawerScreen.Favorite.DrawerScreenSaver
import com.example.appfilm.presentation.ui.home.viewmodel.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,

    homeViewModel: HomeViewModel = hiltViewModel()

) {

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()  // Yêu cầu quyền truy cập email
        .build()
    val googleSignInClient = GoogleSignIn.getClient(LocalContext.current, gso)


    //------------------------------
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    var selectedScreen by rememberSaveable(stateSaver = DrawerScreenSaver) {
        mutableStateOf<DrawerScreen>(DrawerScreen.Home)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                selectedScreen = selectedScreen,
                onItemSelected = {
                    selectedScreen = it
                    coroutineScope.launch { drawerState.close() }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(selectedScreen.title) },
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedScreen) {
                    is DrawerScreen.Home -> Test0()
                    is DrawerScreen.Favorite -> Test1()
                    is DrawerScreen.Settings -> Test2()
                }
            }
        }
    }


}


@Composable
fun DrawerContent(
    selectedScreen: DrawerScreen,
    onItemSelected: (DrawerScreen) -> Unit
) {
    val items = listOf(
        DrawerScreen.Home,
        DrawerScreen.Favorite,
        DrawerScreen.Settings
    )

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(250.dp)
            .background(Color.White)
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {
        items.forEach { screen ->
            val isSelected = selectedScreen.title == screen.title
            val backgroundColor = if (isSelected) Color(0xFFE0E0E0) else Color.Transparent
            val textColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundColor)
                    .clickable { onItemSelected(screen) }
                    .padding(vertical = 12.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(screen.icon, contentDescription = screen.title, tint = textColor)
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = screen.title, color = textColor)
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}



@Composable
fun DrawerItem(
    screen: DrawerScreen,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color(0xFFE0E0E0) else Color.Transparent
    val contentColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = screen.icon, contentDescription = null, tint = contentColor)
        Spacer(modifier = Modifier.width(16.dp))
        Text(screen.title, color = contentColor)
    }
}



@Composable
fun Test1() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text("this is test 1")

    }

}

@Composable
fun Test2() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text("this is test 2")

    }

}

@Composable
fun Test0() {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text("this is test 0")

    }

}

@Composable
fun DrawerItem(
    screen: DrawerScreen,
    isSelected: Boolean,
    onClick: () -> Unit,
    message: String
) {
    val backgroundColor = if (isSelected) Color(0xFFE0E0E0) else Color.Transparent

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                backgroundColor
//                Brush.verticalGradient(listOf(Color.Transparent, Color.LightGray.copy(
//                    alpha = 0.5f
//                )))
            )
            .clickable {  }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = null,
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontSize = 19.sp
            ),
            color = Color.White
        )
    }
}

//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        val homeUIState by homeViewModel.homeUIState.collectAsState()
//
//
//        Text("Home")
//        Button(onClick = {
//
//            homeViewModel.logout(googleSignInClient)
//
//        }) {
//
//            Text("LOG OUT")
//        }
//        if (homeUIState.isSuccess) {
//            navController.navigate(Constants.FIRST_ROUTE) {
//                popUpTo(Constants.HOME_ROUTE) { inclusive = true }
//            }
//        }
//    }
