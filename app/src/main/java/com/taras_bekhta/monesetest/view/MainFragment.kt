package com.taras_bekhta.monesetest.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import androidx.navigation.fragment.findNavController
import com.taras_bekhta.monesetest.R
import com.taras_bekhta.monesetest.injection.MainModule
import com.taras_bekhta.monesetest.injection.component.DaggerMainInjector
import com.taras_bekhta.monesetest.injection.component.MainInjector
import com.taras_bekhta.monesetest.view.adapter.LaunchesAdapter
import com.taras_bekhta.monesetest.viewmodel.LaunchesViewModel
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment: BaseFragment(), LaunchesAdapter.LaunchItemClickListener {
    private lateinit var viewModel: LaunchesViewModel

    private var isLoadingDisposable: Disposable? = null
    private var launchesDisposable: Disposable? = null
    private var errorDisposable: Disposable? = null

    private var selectedSortType: LaunchesAdapter.SortType = LaunchesAdapter.SortType.SORT_BY_DATE
    private var isAscending: Boolean = true

    private lateinit var filterMenuItem: MenuItem

    @Inject
    lateinit var launchesAdapter: LaunchesAdapter

    private fun inject() {
        val injector: MainInjector = DaggerMainInjector
            .builder()
            .mainModule(MainModule(context!!))
            .build()

        when(this) {
            is MainFragment -> injector.inject(this)
        }
    }

    override fun getTitle(): String {
        return "Main Fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        inject()
        viewModel = ViewModelProviders.of(this).get(LaunchesViewModel::class.java)

        viewModel.loadLaunches()
        refreshLayout.isRefreshing = true

        val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(context!!, R.drawable.tasks_divider)!!)
        launchesRecyclerView.addItemDecoration(itemDecorator)
        launchesRecyclerView.layoutManager = LinearLayoutManager(parentActivity)

        launchesAdapter.setListener(this)
        launchesRecyclerView.adapter = launchesAdapter

        refreshLayout.setOnRefreshListener {
            viewModel.loadLaunches()
        }

        isLoadingDisposable = viewModel.isLoadingObservable.subscribe {
            refreshLayout.isRefreshing = it
        }

        launchesDisposable = viewModel.launchesObservable.subscribe {
            launchesAdapter.setLaunches(it)
            filterMenuItem.isChecked = false
            launchesAdapter.sortLaunches(selectedSortType, isAscending)
        }

        errorDisposable = viewModel.errorObservable.subscribe() {
            showErrorSnackBar(it, refreshLayout)
        }
    }

    override fun launchItemClicked(id: String) {
        val dir = MainFragmentDirections.actionMainFragmentToRocketDetailsFragment(id)
        findNavController().navigate(dir)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)

        filterMenuItem = menu.findItem(R.id.action_filter_upcoming)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_filter_upcoming -> {
                item.isChecked = !item.isChecked
                launchesAdapter.showUpcomingOnly(item.isChecked)
                return true
            }
            R.id.action_sort_name -> {
                selectedSortType = LaunchesAdapter.SortType.SORT_BY_NAME
            }
            R.id.action_sort_date -> {
                selectedSortType = LaunchesAdapter.SortType.SORT_BY_DATE
            }
            R.id.action_order_ascending -> {
                isAscending = true
            }
            R.id.action_order_descending -> {
                isAscending = false
            }
            else -> return false
        }

        launchesAdapter.sortLaunches(selectedSortType, isAscending)

        return true
    }

    override fun onDestroy() {
        super.onDestroy()

        isLoadingDisposable?.dispose()
        launchesDisposable?.dispose()
        errorDisposable?.dispose()
    }

}
