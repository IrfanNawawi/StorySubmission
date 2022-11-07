package id.heycoding.storysubmission

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import id.heycoding.storysubmission.data.remote.WebServices
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private val tvLog: TextView by lazy { findViewById(R.id.txt_log) }
    private val disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val services = WebServices.create()
        tvLog.setOnClickListener {
            val userDisposable = services.getAllStories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    println("loading.....")
                    tvLog.text = "loading...."
                }
                .doOnError {
                    println("error anjay -> ${it.message}")
                }
                .subscribe({
                    val isSuccess = it.isSuccessful
                    if (isSuccess) {
                        println("sukses nih")
                        tvLog.text = "sukses nih"
                    } else {
                        tvLog.text = "error coy"
                    }
                }, {
                    println("error di rx")
                    it.printStackTrace()
                })

            disposables.add(userDisposable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
    }
}