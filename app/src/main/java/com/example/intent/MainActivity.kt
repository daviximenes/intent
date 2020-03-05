package com.example.intent

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.Settings
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val listView = ListView(this)
        setContentView(listView)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_expandable_list_item_1,
            resources.getStringArray(R.array.intent_actions)
            )
        listView.adapter = adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            openIntentAtPosition(position)
        }
    }
    private fun openIntentAtPosition(position: Int){
        val uri: Uri? // utilizado para criar a Intent - Uniform Resource Indentifier
        val intent: Intent?
        when(position){
            0 -> { //Abrindo uma URL
                uri = Uri.parse("http://www.google.com.br")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }
            1 -> { //Realiza uma chamada
                uri = Uri.parse("tel:999887766")
                intent = Intent(Intent.ACTION_DIAL, uri)
                openIntent(intent)
            }
            2 -> { //Pesquisa uma posição do mapa
                uri = Uri.parse("geo:0,0?q=Paulus Potterstraat,Amsterdã")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }
            3 -> { //Abrindo editor de SMS
                uri = Uri.parse("sms:12345")
                intent = Intent(Intent.ACTION_VIEW, uri)
                    .putExtra("smsbody","Corpo do SMS")
                openIntent(intent)
            }
            4 -> { //Compartilhar
                intent = Intent()
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_TEXT, "Compartilhar via Intent")
                    .setType("text/plain")
                openIntent(intent)
            }
            5 -> { //Alarme
                intent = Intent(AlarmClock.ACTION_SET_ALARM)
                    .putExtra(AlarmClock.EXTRA_MESSAGE, "Estudar android")
                    .putExtra(AlarmClock.EXTRA_HOUR, 19)
                    .putExtra(AlarmClock.EXTRA_MINUTES, 0)
                    .putExtra(AlarmClock.EXTRA_SKIP_UI, true)
                    .putExtra(AlarmClock.EXTRA_DAYS, arrayListOf(
                        Calendar.MONDAY,
                        Calendar.WEDNESDAY,
                        Calendar.FRIDAY
                    ))
                openIntent(intent)
            }
            6 -> { //Busca na Web
                intent = Intent(Intent.ACTION_SEARCH)
                    .putExtra(SearchManager.QUERY, "Novatec" )
                openIntent(intent)
            }
            7 -> { //Config do aparelho
                intent = Intent(Settings.ACTION_SETTINGS)
                openIntent(intent)
            }
            8 -> { //Açao Compartilhada 1
                intent = Intent("com.example.CUSTOM_ACTION")
                openIntent(intent)
            }
            9 -> { //Ação compartilhada 2
                uri = Uri.parse("produto://Notebook/Slim")
                intent = Intent(Intent.ACTION_VIEW, uri)
                openIntent(intent)
            }
            else -> finish()


        }
    }

    private fun openIntent(intent: Intent){
        if (intent.resolveActivity(packageManager)!= null){
            startActivity(intent)
        } else {
            Toast.makeText(this,R.string.error_intent, Toast.LENGTH_SHORT).show()
        }
    }
}
