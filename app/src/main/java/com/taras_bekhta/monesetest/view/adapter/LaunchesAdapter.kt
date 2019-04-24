package com.taras_bekhta.monesetest.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.taras_bekhta.monesetest.R
import com.taras_bekhta.monesetest.model.Launch
import kotlinx.android.synthetic.main.launch_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class LaunchesAdapter @Inject constructor(private var context: Context): RecyclerView.Adapter<LaunchesAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yy")

    private var originalLaunches: ArrayList<Launch> = arrayListOf()
    private var launches: ArrayList<Launch> = arrayListOf()
    private var listener: LaunchItemClickListener? = null

    fun setLaunches(newLaunches: ArrayList<Launch>) {
        launches.clear()
        launches.addAll(newLaunches)

        originalLaunches.clear()
        originalLaunches.addAll(newLaunches)

        notifyDataSetChanged()
    }

    fun setListener(newListener: LaunchItemClickListener) {
        listener = newListener
    }

    override fun getItemCount(): Int {
        return launches.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.launch_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currLaunch = launches[position]

        holder.launchNumberTextView.text = "${currLaunch.flightNumber}. "
        holder.launchTitleTextView.text = currLaunch.missionName

        val launchDate = Calendar.getInstance()
        launchDate.timeInMillis = currLaunch.launchDateUTC * 1000
        holder.launchDateTextView.text = dateFormat.format(launchDate.time)

        holder.itemView.setOnClickListener {
            listener?.launchItemClicked(currLaunch.rocketInfo.rocketId)
        }
    }

    fun sortLaunches(type: SortType, isAscending: Boolean) {
        when(type) {
            SortType.SORT_BY_NAME -> {
                launches.sortWith(Comparator { lhs, rhs ->
                    if (isAscending) lhs.missionName.compareTo(rhs.missionName) else rhs.missionName.compareTo(lhs.missionName)
                })
            }
            SortType.SORT_BY_DATE -> {
                launches.sortWith(Comparator { lhs, rhs -> if(isAscending) lhs.launchDateUTC.compareTo(rhs.launchDateUTC) else rhs.launchDateUTC.compareTo(lhs.launchDateUTC) })
            }
        }
        notifyDataSetChanged()
    }

    fun showUpcomingOnly(upcomingOnly: Boolean) {
        if(upcomingOnly) {
            val filteredList = launches.filter { item -> item.launchDateUTC * 1000 > Calendar.getInstance().timeInMillis }
            launches.clear()
            launches.addAll(filteredList)
        } else {
            launches.clear()
            launches.addAll(originalLaunches)
        }
        notifyDataSetChanged()
    }

    interface LaunchItemClickListener {
        fun launchItemClicked(id: String)
    }

    enum class SortType {
        SORT_BY_NAME,
        SORT_BY_DATE
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val launchNumberTextView: TextView = view.launchNumberTextView
        val launchTitleTextView: TextView = view.launchTitleTextView
        val launchDateTextView: TextView = view.launchDateTextView
    }
}