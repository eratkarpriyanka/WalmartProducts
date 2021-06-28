package com.walmartlabstest.walmartproducts.uiutils

class StringUtils private constructor() {
    fun isNullEmpty(text: String?): Boolean {
        return if (text == null || text.isEmpty()) {
            true
        } else false
    }

    companion object {
        const val products = "products"
        var instance: StringUtils? = null
            get() {
                if (field == null) {
                    field = StringUtils()
                }
                return field
            }
            private set
        var position = "positionSelected"
    }
}