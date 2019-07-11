package com.scancriteria.scanproperty

data class ScanProperty(
    val id: Int, val name: String, val tag: String, val color: String,
    val criteria: List<Criteria>
) {

    data class Criteria(val type: String, val text: String?, val variable: Map<String, VariableObj>?)

    data class VariableObj(
        val type: String?, val study_type: String?, val parameter_name: String?,
        val min_value: String?, val max_value: String?, val default_value: String?, val values: List<Double>?
    )
}