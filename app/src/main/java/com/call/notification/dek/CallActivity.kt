package com.call.notification.dek

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.call.notification.dek.service.ServiceRepository
import com.call.notification.dek.ui.theme.MainTheme
import com.call.notification.dek.ui.theme.colorPrimary
import com.call.notification.dek.utils.ViewModelFactory

class CallActivity : ComponentActivity() {

    private lateinit var viewModel: CallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainRepository = ServiceRepository(this)
        viewModel = ViewModelProvider(this, ViewModelFactory(mainRepository))[CallViewModel::class.java]
//        viewModel.onFinished.observe(this) {
//            if (it) {
//                finish()
//            }
//        }

        val imgUrl = intent.getStringExtra("img_url");

        setContent {
            MainTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box() {
                        Profile(imgUrl)
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
                                textAlign = TextAlign.Center,
                                text = "Calling...",
                                style = TextStyle(
                                    fontSize = 30.sp,
                                    fontWeight =  FontWeight.Bold,
                                    color = colorPrimary
                                )
                            )
                            Text(
                                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                                textAlign = TextAlign.Center,
                                text = "Wutdy Wuttichai",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight =  FontWeight.Bold,
                                    color = colorPrimary
                                )
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            ButtonAction(
                                answer = {
                                    viewModel.answer(this@CallActivity)
                                },
                                decline = {
                                    viewModel.answer(this@CallActivity)
                                }
                            )
                        }
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MainTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box() {
                Profile("")
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 50.dp),
                        textAlign = TextAlign.Center,
                        text = "Calling...",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight =  FontWeight.Bold,
                            color = colorPrimary
                        )
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                        textAlign = TextAlign.Center,
                        text = "Wutdy Wuttichai",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight =  FontWeight.Bold,
                            color = colorPrimary
                        )
                    )
                }
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    ButtonAction(answer = {}, decline = {})
                }
            }
        }
    }
}

@Composable
fun ButtonAction(answer: () -> Unit, decline: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(start = 40.dp, end = 40.dp, bottom = 20.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = { answer() },
            colors = ButtonDefaults.buttonColors(
                containerColor = colorPrimary
            )
        ) {
            Icon(
                modifier = Modifier
                    .size(width = 30.dp, height = 30.dp)
                    .padding(end = 5.dp),
                painter = painterResource(id = R.drawable.ic_call),
                contentDescription = null // decorative element
            )
            Text("Answer")
        }

        Button(
            onClick = { decline() },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red
            )
        ) {
            Icon(
                modifier = Modifier
                    .size(width = 30.dp, height = 30.dp)
                    .padding(end = 5.dp),
                painter = painterResource(id = R.drawable.ic_hangup),
                contentDescription = null // decorative element
            )
            Text("Decline")
        }
    }
}

@Composable
fun Profile(url: String?) {
    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        placeholder = painterResource(R.drawable.man),
    )
}
