package com.call.notification.dek

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import com.call.notification.dek.firebase.FirebaseManagement
import com.call.notification.dek.service.ServiceRepository
import com.call.notification.dek.ui.theme.MainTheme
import com.call.notification.dek.utils.PermissionUtils
import com.call.notification.dek.utils.ViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.gson.Gson

class MainActivity : ComponentActivity() {

    val TAG = "log-firebase-message"
    val TAGDB = "log-call-history"
    lateinit var viewModel: CallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerFirebase()

        val mainRepository = ServiceRepository(this)
        viewModel =
            ViewModelProvider(this, ViewModelFactory(mainRepository))[CallViewModel::class.java]

        setContent {
            MainTheme {
                Scaffold(
                    topBar = {
                        AppBar()
                    },
                    content = { innerPadding ->
                        HomeContent(viewModel, innerPadding)
                    },
                    bottomBar = {
                        bottomBar()
                    }
                )
            }
        }
    }


    private fun registerFirebase() {
        Firebase.messaging.isAutoInitEnabled = true
        Firebase.messaging.token.addOnCompleteListener(OnCompleteListener { task ->
            val token = task.result
            Log.d(TAG, "is token => $token")
        })

        var permission = arrayOf(Manifest.permission.POST_NOTIFICATIONS)
        if (PermissionUtils.requestPermission(this, requestPermissionLauncher, permission)) {
            setUpFirebase()
        }
    }

    private fun setUpFirebase() {
        FirebaseManagement.createChannel(this)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        setUpFirebase()
    }
}

@Composable
fun HomeContent(viewModel: CallViewModel, innerPadding: PaddingValues) {
//    val callHistory = viewModel.callHistory.collectAsState()
//    val callHistory = viewModel.callHistory

    val callHistory by rememberUpdatedState(newValue = viewModel.callHistory.value)

    LaunchedEffect(callHistory) {
        Log.d("log-sadasd",Gson().toJson(callHistory))
    }

    viewModel.feedCallHistory()
//    val context = LocalContext.current
    LazyColumn(
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

//        viewModel.callHistory.observe(context) {

            items(count = 10) { i ->
                Text(
                    text = "dddd",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
//        }
    }
}


@Composable
fun MainPreview() {
    Scaffold(
        topBar = {
            AppBar()
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..10).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        },
        bottomBar = {
            bottomBar()
        }
    )
}


@Preview(showBackground = true)
@Composable
fun bottomBar() {
    BottomAppBar(
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(Icons.Filled.Check, contentDescription = "Localized description")
            }
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "Localized description",
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        title = {
            Text(
                "Welcome",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            IconButton(onClick = { /* doSomething() */ }) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Setting"
                )
            }
        }
    )
}
