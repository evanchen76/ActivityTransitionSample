package evan.chen.tutorial.activitytransitionsample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.widget.GridLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainAdapter.OnItemClickListener{

    lateinit var scenerys: List<Scenery>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scenerys = Scenery.scenerys

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        val mainAdapter = MainAdapter(this, scenerys)
        mainAdapter.listener = this
        recyclerView.adapter = mainAdapter
    }

    override fun onItemClick(view: View, position: Int) {
        val item = scenerys[position]

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.ARG_SCENERY_ID, item.sceneryId)

        //步驟1：取得imageView、title
        val imageView = view!!.findViewById<View>(R.id.sceneryImageView)
        val title = view.findViewById<View>(R.id.sceneryTitle)

        //步驟2：將imageView、title 設定Transition Name，這裡的Transition Name會與DetailActivity一樣
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(imageView, DetailActivity.TRANSITION_SCENERY_IMAGE_NAME),
                Pair(title, DetailActivity.TRANSITION_SCENERY_TITLE_NAME)
        )

        //步驟3：startActivity，並將activityOptions傳入
        startActivity(intent, activityOptions.toBundle())
    }

}
