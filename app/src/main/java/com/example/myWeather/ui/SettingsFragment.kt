package com.example.myWeather.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myWeather.R

class SettingsFragment : Fragment() {

    private lateinit var prefs: SharedPreferences

    private lateinit var rgTemperature: RadioGroup
    private lateinit var rbCelcius: RadioButton
    private lateinit var rbFahrenheit: RadioButton

    private lateinit var radiogroupLanguage: RadioGroup
    private lateinit var rbPortuguese: RadioButton
    private lateinit var rbEnglish: RadioButton

    private var temperatureUnit = ""
    private var language = ""


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val settingsContainer =
            inflater.inflate(R.layout.fragment_settings, container, false)

        val textView = settingsContainer.findViewById<TextView>(R.id.settings_text)

        textView.text = getString(R.string.title_settings)

        return settingsContainer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = view.context.getSharedPreferences("my_weather_prefs", 0)

        rbCelcius = view.findViewById(R.id.radiobuttonC)
        rbFahrenheit = view.findViewById(R.id.radiobuttonF)
        rbPortuguese = view.findViewById(R.id.rbPortuguese)
        rbEnglish = view.findViewById(R.id.rbEnglish)


        temperatureUnit = prefs?.getString("temperature_unit", "F").toString()
        when(temperatureUnit){
            "C" -> rbCelcius.isChecked = true
            "F" -> rbFahrenheit.isChecked = true
        }

        language = prefs?.getString("language", "EN").toString()
        when(language){
            "PT" -> rbPortuguese.isChecked = true
            "EN" -> rbEnglish.isChecked = true
        }

        rgTemperature = view.findViewById(R.id.rgTemperature)
        radiogroupLanguage = view.findViewById(R.id.radiogroupLanguage)

        rgTemperature.setOnCheckedChangeListener { view, id ->
            val radioButton = view.findViewById<RadioButton>(id)
            val editor = prefs?.edit()

            if (radioButton.isChecked) {
                when (radioButton.id) {
                    R.id.radiobuttonC -> editor?.apply {putString("temperature_unit", "C").apply() }
                    R.id.radiobuttonF -> editor?.apply {putString("temperature_unit", "F").apply() }
                }
                Toast.makeText(this.context, "Saved", Toast.LENGTH_SHORT).show()
            }
        }

        radiogroupLanguage.setOnCheckedChangeListener { view, id ->
            val radioButton = view.findViewById<RadioButton>(id)
            val editor = prefs?.edit()

            if (radioButton.isChecked) {
                when (radioButton.id) {
                    R.id.rbPortuguese -> editor?.apply {putString("language", "PT").apply() }
                    R.id.rbEnglish -> editor?.apply {putString("language", "EN").apply() }
                }
                Toast.makeText(this.context, "Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }


}