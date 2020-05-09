package com.ishroid.example.app.utils

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ColumnType
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.api.PreparedStatementApi
import java.lang.StringBuilder

fun Table.arrayOfString(name: String): Column<List<String>> =
    registerColumn(name, StringArrayColumnType())

internal class StringArrayColumnType : ColumnType() {
    override fun sqlType() = "varchar"
    override fun valueFromDB(value: Any): List<String> {
        return if (value is String) {
            value.split(",")
        } else
            emptyList()
    }

    override fun valueToString(value: Any?): String {
        return if (value is List<*>) {
            val finalResult = StringBuilder()
            value.forEachIndexed { index, any ->
                if (index != 0)
                    finalResult.append(",")
                finalResult.append(any)
            }
            finalResult.toString()
        } else {
            ""
        }
    }

    override fun setParameter(stmt: PreparedStatementApi, index: Int, value: Any?) {
        if (value is List<*>) {
            stmt[index] = valueToString(value)
        } else
            super.setParameter(stmt, index, value)
    }

}