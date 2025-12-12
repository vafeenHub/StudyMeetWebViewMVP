package com.example.studymate

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.studymate.ui.theme.AppTheme
import com.example.studymate.ui.theme.MainTheme

internal class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			MainTheme {
				Scaffold(
					modifier = Modifier.fillMaxSize(),
					containerColor = AppTheme.colors.background
				) { innerPadding ->
					WebViewScreen(
						modifier = Modifier
							.fillMaxSize()
							.padding(innerPadding),
						url = "http://194.87.207.73/home"
					)
				}
			}
		}
	}
}

@Composable
fun WebViewScreen(
	modifier: Modifier = Modifier,
	url: String,
) {
	var webView by remember { mutableStateOf<WebView?>(null) }

	BackHandler(enabled = true) {
		webView?.let { wv ->
			if (wv.canGoBack()) {
				wv.goBack()
			}
		}
	}

	AndroidView(
		modifier = modifier,
		factory = { context ->
			WebView(context).apply {
				layoutParams = ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT
				)
				settings.apply {
					javaScriptEnabled = true
					domStorageEnabled = true
					cacheMode = WebSettings.LOAD_DEFAULT
					mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
				}
				webViewClient = object : WebViewClient() {
					override fun onReceivedError(
						view: WebView?,
						request: WebResourceRequest?,
						error: WebResourceError?
					) {
						super.onReceivedError(view, request, error)
						Log.e("WebView", "Error: ${error?.description}")
					}
				}
				loadUrl(url)
				webView = this
			}
		},
		update = { webViewInstance ->
			webView = webViewInstance
			webViewInstance.loadUrl(url)
		}
	)
}

