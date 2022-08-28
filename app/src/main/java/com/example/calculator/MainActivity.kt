package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    var stackChecComma = ArrayDeque<Boolean>()
    val operators: Array<Char> = arrayOf('+','−', '*', '/')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btn_0.setOnClickListener { firstClick(); setNumberInput(btn_0.text.toString()) }
        btn_1.setOnClickListener { firstClick(); setNumberInput(btn_1.text.toString()) }
        btn_2.setOnClickListener { firstClick(); setNumberInput(btn_2.text.toString()) }
        btn_3.setOnClickListener { firstClick(); setNumberInput(btn_3.text.toString()) }
        btn_4.setOnClickListener { firstClick(); setNumberInput(btn_4.text.toString()) }
        btn_5.setOnClickListener { firstClick(); setNumberInput(btn_5.text.toString()) }
        btn_6.setOnClickListener { firstClick(); setNumberInput(btn_6.text.toString()) }
        btn_7.setOnClickListener { firstClick(); setNumberInput(btn_7.text.toString()) }
        btn_8.setOnClickListener { firstClick(); setNumberInput(btn_8.text.toString()) }
        btn_9.setOnClickListener { firstClick(); setNumberInput(btn_9.text.toString()) }

        AC_btn.setOnClickListener { input.text = "0"; stackChecComma.clear(); result.text = "" }
        CL_btn.setOnClickListener {
            val len = input.text.length
            val ch = input.text[len - 1]
            if (ch == ',' && stackChecComma.first()){
                stackChecComma.removeFirst()
                stackChecComma.addFirst(false)
            }
            for(operator in operators){
                if (ch == operator){
                    stackChecComma.removeFirst()
                    break
                }
            }
            if (len == 1) input.text = "0"
            else input.text = input.text.removeRange(len - 1, len)
        }

        bracket_left_btn.setOnClickListener {
            firstClick()
            setNumberInput(bracket_left_btn.text.toString())
        }

        bracket_right_btn.setOnClickListener {
            firstClick()
            setNumberInput(bracket_right_btn.text.toString())
        }

        minus_btn.setOnClickListener { setActionInput("-"); stackChecComma.addFirst(false) }
        multiply_btn.setOnClickListener {  setActionInput(multiply_btn.text.toString()); stackChecComma.addFirst(false) }
        division_btn.setOnClickListener { setActionInput(division_btn.text.toString()); stackChecComma.addFirst(false) }
        plus_btn.setOnClickListener { setActionInput(plus_btn.text.toString()); stackChecComma.addFirst(false) }

        comma_btn.setOnClickListener { setCommaInput(comma_btn.text.toString()); }

        result_btn.setOnClickListener {
            try {
                val ex = ExpressionBuilder(input.text.toString()).build()
                val _result = ex.evaluate()

                val longRes = _result.toLong()
                if (_result ==  longRes.toDouble()){
                    result.text = longRes.toString()
                } else{
                    result.text = _result.toString()
                }
            } catch (e: Exception){
                result.text = "ERROR! Please chek the expresion"
            }
        }
    }

    fun setNumberInput(std: String) {
        input.append(std);
    }

    fun setActionInput(std: String) {
        if (!result.text.isEmpty()){
            input.text = result.text
            result.text = ""
        }

        val len = input.text.length
        if (len == 1 && input.text[len - 1] == '0') return
        val ch = input.text[len - 1]
        if (ch == '*' || ch == '/' || ch == '+' || ch == '−') input.text = input.text.removeRange(len - 1, len)
        input.append(std);
    }

    fun setCommaInput(std: String) {
        val len = input.text.length
        if (len == 1 && input.text[len - 1] == '0') return
        if (!stackChecComma.isEmpty()){
            if (!stackChecComma.first()){
                input.append(std);
                stackChecComma.removeFirst()
                stackChecComma.addFirst(true)
            }
        }


    }

    fun firstClick() {
        if (stackChecComma.isEmpty()) stackChecComma.addFirst(false)
        if (input.text[0] == '0' && input.text.length == 1) {
            input.text = ""
        }
    }

}
