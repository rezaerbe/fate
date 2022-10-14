package com.erbeandroid.fate

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.erbeandroid.fate.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import it.skrape.core.htmlDocument
import it.skrape.selects.attribute
import it.skrape.selects.html5.a
import it.skrape.selects.html5.img
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*        mainViewModel.servantState.launchAndCollectIn(this) { servantState ->
            when (servantState) {
                is StateData.Loading -> Log.d("TAG", "Loading")
                is StateData.Success -> Log.d("TAG", servantState.data.toString())
                is StateData.Error -> Log.d("TAG", servantState.exception.toString())
            }
        }*/

        mainViewModel.eventState.launchAndCollectIn(this) { eventState ->
            binding.progressBar.isVisible = eventState is StateData.Loading
            binding.webView.isVisible = eventState is StateData.Success
            when (eventState) {
                is StateData.Loading -> Log.d("TAG", "Loading")
                is StateData.Success -> {
                    Log.d("TAG", eventState.data.toString())
                    val unEncodedHtml = eventState.data?.text
                    var title = ""
                    var image = ""
                    unEncodedHtml?.let {
                        htmlDocument(unEncodedHtml) {
                            a {
                                findAll {
                                    title = attribute("title")
                                }
                            }
                            img {
                                findAll {
                                    image = attribute("data-src")
                                }
                            }
                        }
                    }
                    Log.d("TAGOK", title.split(",").toString())
                    Log.d("TAGOK", image.split(",").toString())
/*                    unEncodedHtml?.let {
                        val encodedHtml =
                            Base64.encodeToString(unEncodedHtml.toByteArray(), Base64.NO_PADDING)
                        binding.webView.loadData(
                            encodedHtml,
                            "text/html",
                            "base64"
                        )
                        binding.webView.loadDataWithBaseURL(
                            null,
                            unEncodedHtml,
                            "text/html",
                            "utf-8",
                            null
                        )
                    }*/
                }
                is StateData.Error -> Log.d("TAG", eventState.exception.toString())
            }
        }
    }
}

inline fun <T> Flow<T>.launchAndCollectIn(
    owner: LifecycleOwner,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline action: suspend CoroutineScope.(T) -> Unit
) = owner.lifecycleScope.launch {
    owner.repeatOnLifecycle(minActiveState) {
        collect {
            action(it)
        }
    }
}