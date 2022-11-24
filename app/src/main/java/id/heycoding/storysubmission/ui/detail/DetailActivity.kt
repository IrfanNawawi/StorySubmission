package id.heycoding.storysubmission.ui.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.heycoding.storysubmission.databinding.ActivityDetailBinding
import id.heycoding.storysubmission.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    private var activityDetailBinding: ActivityDetailBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding?.root)

        supportActionBar?.hide()
        initView()
        showDetail()
    }


    private fun initView() {
        activityDetailBinding?.fabShowInfo?.setOnClickListener {
            activityDetailBinding?.detailStoryContainer?.visibility = View.VISIBLE
        }

        activityDetailBinding?.tvDetailClose?.setOnClickListener {
            activityDetailBinding?.detailStoryContainer?.visibility = View.GONE
        }
    }

    private fun showDetail() {
        val name = intent.getStringExtra("NAME")
        val desc = intent.getStringExtra("DESC")
        val img = intent.getStringExtra("IMAGE")

        activityDetailBinding?.apply {
            tvStoryDetailDesc.text = desc
            tvStoryTitle.text = name
            Glide.with(this@DetailActivity)
                .load(img)
                .into(storyImage)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityDetailBinding = null
    }
}