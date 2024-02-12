package com.prasad.abbreviationsfinder.view.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import com.prasad.abbreviationsfinder.R

class CustomEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var editText: EditText
    private var clearIcon: ImageView
    private var searchIcon: ImageView
    private var bookmarkIcon: ImageView

    init {
        inflate(context, R.layout.custom_edit_text, this)
        editText = findViewById(R.id.editText)
        clearIcon = findViewById(R.id.clearIcon)
        searchIcon = findViewById(R.id.searchIcon)
        bookmarkIcon = findViewById(R.id.bookmarkIcon)

        // Clear text when clear icon is clicked
        clearIcon.setOnClickListener {
            editText.text = null
        }

        // Add text change listener to show/hide clear icon
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearIcon.visibility = if ((s?.length ?: 0) > 0) View.VISIBLE else View.GONE
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // Implement your search and bookmark functionality here
    }
}
