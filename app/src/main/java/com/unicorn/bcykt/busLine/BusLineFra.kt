package com.unicorn.bcykt.busLine

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.services.busline.BusLineQuery
import com.amap.api.services.busline.BusLineSearch
import com.unicorn.bcykt.R
import kotlinx.android.synthetic.main.fra_bus_line.*
import me.yokeyword.fragmentation.SupportFragment

class BusLineFra : SupportFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fra_bus_line, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        searchBusLine("3")
    }

    private fun searchBusLine(keyWord: String) {
        BusLineSearch(context, BusLineQuery(keyWord, BusLineQuery.SearchType.BY_LINE_NAME, "021")).apply {
            setOnBusLineSearchListener { result, code ->
                busLineAdapter.setNewData(result.busLines)
            }
        }.searchBusLineAsyn()
    }

    private val busLineAdapter = BusLineAdapter()

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            busLineAdapter.bindToRecyclerView(this)
        }
        busLineAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(context,BusLineAct::class.java)
            intent.putExtra("keg",busLineAdapter.getItem(position))
            startActivity(intent)
//            BusLineActStarter.start(context,"sdf")
//            BusLineActStarter.start(context, busLineAdapter.getItem(position))
        }
    }

}