package com.taras_bekhta.monesetest.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.taras_bekhta.monesetest.R
import com.taras_bekhta.monesetest.model.Rocket
import com.taras_bekhta.monesetest.viewmodel.RocketDetailsViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.rocket_details_fragment.*
import java.text.DecimalFormat

class RocketDetailsFragment : BaseFragment() {

    private lateinit var viewModel: RocketDetailsViewModel

    private var isLoadingDisposable: Disposable? = null
    private var rocketDisposable: Disposable? = null
    private var errorDisposable: Disposable? = null

    private val decimalFormat = DecimalFormat("#,###")

    override fun getTitle(): String {
        return "Rocket Details"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.rocket_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RocketDetailsViewModel::class.java)

        val rocketId = RocketDetailsFragmentArgs.fromBundle(arguments!!).rocketId

        isLoadingDisposable = viewModel.isLoadingObservable.subscribe {
            if(it) {
                rocketProgressBar.visibility = View.VISIBLE
                rocketLayout.visibility = View.INVISIBLE
            } else {
                rocketProgressBar.visibility = View.INVISIBLE
                rocketLayout.visibility = View.VISIBLE
            }
        }

        rocketDisposable = viewModel.rocketObservable.subscribe {
            displayRocket(it)
        }

        errorDisposable = viewModel.errorObservable.subscribe {
            showErrorSnackBar(it, refreshLayout)
        }

        viewModel.loadRocket(rocketId)
    }

    private fun displayRocket(rocket: Rocket) {
        nameTextView.text = rocket.rocketName
        activeTextView.text = if(rocket.isActive) getString(R.string.active) else getString(R.string.not_active)
        stagesTextView.text = "${getString(R.string.stages)}${rocket.stages}"
        costTextView.text = "${getString(R.string.cost_per_launch)}${decimalFormat.format(rocket.costPerLaunch)} USD"
        descriptionTextView.text = rocket.description
        wikiTextView.text = rocket.wikipediaUrl
    }

    override fun onDestroy() {
        super.onDestroy()

        isLoadingDisposable?.dispose()
        rocketDisposable?.dispose()
        errorDisposable?.dispose()
    }
}
