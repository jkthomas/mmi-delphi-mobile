package com.example.mmi_delphi_mobile.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v4.app.Fragment
import android.arch.lifecycle.ViewModelProviders
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.Half.toFloat
import android.widget.RadioButton
import com.example.mmi_delphi_mobile.R
import com.example.mmi_delphi_mobile.utilities.enums.ViewElementType
import org.json.JSONArray
import org.json.JSONObject

/**
 * A placeholder fragment containing a simple view.
 */
class FeedFragment : Fragment() {

    private lateinit var feedViewModel: FeedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedViewModel = ViewModelProviders.of(this, FeedViewModelFactory()).get(FeedViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val constraintLayout = view.findViewById(R.id.constraintLayout) as ConstraintLayout

        var viewElementIndex = 101

        val pollData: JSONObject = feedViewModel.getPollData()
        val questionsArray: JSONArray = pollData.getJSONArray("questions")

        for(index in 0..(questionsArray.length() - 1)){
            val question = questionsArray.getJSONObject(index)
            this.placeViewElement(ViewElementType.TEXT_VIEW, viewElementIndex, constraintLayout, question.getString("content"))
            viewElementIndex += 1

            val answersArray = question.getJSONArray("answers")
            for(index in 0..(answersArray.length() - 1)) {
                val answer = answersArray.getJSONObject(index)
                this.placeViewElement(ViewElementType.RADIO_BUTTON, viewElementIndex, constraintLayout, answer.getString(index.toString()))
                viewElementIndex += 1
            }
        }
    }

    private fun placeViewElement(viewElementType: ViewElementType, viewElementIndex: Int, constraintLayout: ConstraintLayout, content: String){
        val viewElement: View
        var margin: Int = 0
        if(viewElementType == ViewElementType.TEXT_VIEW){
            viewElement = TextView(activity)
            viewElement.text = content
            viewElement.textSize = 20f
            margin = 40
        } else { //ViewElementType.RADIO_BUTTON) case
            viewElement = RadioButton(activity)
            viewElement.text = content
        }
        viewElement.id = viewElementIndex
        viewElement.layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        constraintLayout.addView(viewElement)
        //Setting position
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)

        if((viewElementIndex - 1) == 100) {
            constraintSet.connect(
                viewElement.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP
            )
            constraintSet.connect(
                viewElement.id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT
            )
            constraintSet.connect(
                viewElement.id,
                ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT
            )
        } else {
            constraintSet.connect(
                viewElement.id,
                ConstraintSet.TOP,
                (viewElementIndex - 1),
                ConstraintSet.BOTTOM,
                margin
            )
            constraintSet.connect(
                viewElement.id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT
            )
            if(viewElementType == ViewElementType.TEXT_VIEW) {
                constraintSet.connect(
                    viewElement.id,
                    ConstraintSet.RIGHT,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.RIGHT
                )
            }
        }

        constraintSet.applyTo(constraintLayout)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): FeedFragment {
            return FeedFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}