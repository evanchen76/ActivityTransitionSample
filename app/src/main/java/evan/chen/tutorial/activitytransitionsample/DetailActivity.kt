package evan.chen.tutorial.activitytransitionsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat.setTransitionName
import android.transition.Transition
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val ARG_SCENERY_ID = "ARG_SCENERY_ID"

        const val TRANSITION_SCENERY_IMAGE_NAME = "TRANSITION_SCENERY_IMAGE_NAME"

        const val TRANSITION_SCENERY_TITLE_NAME = "TRANSITION_SCENERY_TITLE_NAME"
    }

    private lateinit var scenery: Scenery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        scenery = Scenery.getItem(intent.getIntExtra(ARG_SCENERY_ID, 0))!!

        //設定TransitionName
        setTransitionName(sceneryImageView, TRANSITION_SCENERY_IMAGE_NAME)
        setTransitionName(sceneryTitle, TRANSITION_SCENERY_TITLE_NAME)

        loadData()
    }

    private fun loadData() {
        sceneryTitle.text = scenery.name
        sceneryDesc.text = scenery.desc

        addTransitionListener()

        //讀取小圖
        loadThumbnail()
    }

    private fun addTransitionListener(): Boolean {
        val transition = window.sharedElementEnterTransition

        if (transition != null) {
            transition.addListener(object : Transition.TransitionListener {
                override fun onTransitionResume(transition: Transition) {

                }

                override fun onTransitionPause(transition: Transition) {

                }

                override fun onTransitionStart(transition: Transition) {

                }

                override fun onTransitionEnd(transition: Transition) {
                    //動畫完成，載入大圖
                    loadFullSizeImage()

                    transition.removeListener(this)
                }

                override fun onTransitionCancel(transition: Transition) {
                    transition.removeListener(this)
                }
            })
            return true
        }

        return false
    }

    private fun loadThumbnail() {
        Picasso.with(sceneryImageView!!.context).load(scenery.thumbUrl)
                .noFade()
                .into(sceneryImageView)
    }

    private fun loadFullSizeImage() {
        Picasso.with(sceneryImageView!!.context).load(scenery.imageUrl)
                .noFade()
                .noPlaceholder()
                .into(sceneryImageView)
    }
}
