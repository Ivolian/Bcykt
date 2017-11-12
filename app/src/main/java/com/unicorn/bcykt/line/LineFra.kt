package com.unicorn.bcykt.line

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.services.busline.BusLineQuery
import com.amap.api.services.busline.BusLineSearch
import com.unicorn.bcykt.R
import com.unicorn.bcykt.line.station.LineAct
import kotlinx.android.synthetic.main.fra_line.*

class LineFra : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fra_line, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initRecyclerView()
        searchBusLine("3")
    }

    private val lineAdapter = LineAdapter()

    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            lineAdapter.bindToRecyclerView(this)
        }
        lineAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent(context, LineAct::class.java)
            intent.putExtra("line", lineAdapter.getItem(position))
            startActivity(intent)
        }
    }

    private fun searchBusLine(keyWord: String) {
        BusLineSearch(context, BusLineQuery(keyWord, BusLineQuery.SearchType.BY_LINE_NAME, "021"))
                .apply {
                    setOnBusLineSearchListener { result, _ ->
                        lineAdapter.setNewData(result.busLines)
                    }
                }.searchBusLineAsyn()
    }

}