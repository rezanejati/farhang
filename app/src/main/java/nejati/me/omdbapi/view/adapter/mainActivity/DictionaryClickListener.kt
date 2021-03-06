package nejati.me.omdbapi.view.adapter.mainActivity

import nejati.me.omdbapi.webServices.farhangModel.dictionary.DictionaryResponse
import nejati.me.omdbapi.webServices.farhangModel.dictionary.DictionaryResult

interface DictionaryClickListener {
    fun itemClicked(t: DictionaryResponse?)
    fun itemMoreClicked(t: DictionaryResult?)
}