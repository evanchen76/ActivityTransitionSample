package evan.chen.tutorial.activitytransitionsample

import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemClickListener {
    private var adapter: GridAdapter? = null

    lateinit var scenerys: List<Scenery>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scenerys = Scenery.scenerys

        adapter = GridAdapter()
        grid.adapter = adapter
        grid.onItemClickListener = this
    }

    override fun onItemClick(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val item = adapterView!!.getItemAtPosition(position) as Scenery

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

    private inner class GridAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return scenerys.size
        }

        override fun getItem(position: Int): Scenery {
            return Scenery.scenerys[position]
        }

        override fun getItemId(position: Int): Long {
            return getItem(position).sceneryId.toLong()
        }

        override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
            var view = view
            if (view == null) {
                view = layoutInflater.inflate(R.layout.grid_item, viewGroup, false)
            }

            val scenery = getItem(position)

            val image = view!!.findViewById(R.id.sceneryImageView) as ImageView

            Picasso.with(image.context).load(scenery.thumbUrl)
                    .noFade()
                    .into(image)

            val name = view.findViewById(R.id.sceneryTitle) as TextView
            name.text = scenery.name

            return view
        }
    }
}
