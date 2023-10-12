package xyz.genesisapp.common.serialization

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.genesisapp.common.interfaces.IDynValue
import java.io.File
import kotlin.reflect.KProperty

class GsonAdapter(val filepath: String) {

    val elem: JsonElement
    init {
        val f = File(filepath)
        if (!f.exists()) {
            f.createNewFile()
        } else {
            if (!f.canRead()) {
                throw Exception("Can't read preferences file")
            }
            if (!f.canWrite()) {
                throw Exception("Can't write preferences file")
            }
        }
        elem = JsonParser.parseString(f.readText().ifEmpty { "{}" })

    }

    fun save() {
        CoroutineScope(Dispatchers.IO).launch {
            File(filepath).writeText(elem.toString())
        }
    }

    fun value(key: String, defaultValue: String): IDynValue<String> {
        return object : IDynValue<String> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): String {
                return elem.asJsonObject[key]?.asString ?: defaultValue
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
                elem.asJsonObject.remove(key)
                elem.asJsonObject.addProperty(key, value)
                save()
            }

        }
    }
    fun value(key: String, defaultValue: Int): IDynValue<Int> {
        return object : IDynValue<Int> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Int {
                return elem.asJsonObject[key]?.asInt ?: defaultValue
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: Int) {
                elem.asJsonObject.remove(key)
                elem.asJsonObject.addProperty(key, value)
                save()
            }

        }
    }
    fun value(key: String, defaultValue: Long): IDynValue<Long> {
        return object : IDynValue<Long> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Long {
                return elem.asJsonObject[key]?.asLong ?: defaultValue
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: Long) {
                elem.asJsonObject.remove(key)
                elem.asJsonObject.addProperty(key, value)
                save()
            }

        }
    }
    fun value(key: String, defaultValue: Float): IDynValue<Float> {
        return object : IDynValue<Float> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Float {
                return elem.asJsonObject[key]?.asFloat ?: defaultValue
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: Float) {
                elem.asJsonObject.remove(key)
                elem.asJsonObject.addProperty(key, value)
                save()
            }

        }
    }
    fun value(key: String, defaultValue: Boolean): IDynValue<Boolean> {
        return object : IDynValue<Boolean> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Boolean {
                return elem.asJsonObject[key]?.asBoolean ?: defaultValue
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: Boolean) {
                elem.asJsonObject.remove(key)
                elem.asJsonObject.addProperty(key, value)
                save()
            }

        }
    }
    fun value(key: String, defaultValue: Set<String>): IDynValue<Set<String>> {
        return object : IDynValue<Set<String>> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): Set<String> {
                return elem.asJsonObject[key]?.asJsonArray?.map { it.asString }?.toSet() ?: defaultValue
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: Set<String>) {
                elem.asJsonObject.remove(key)
                elem.asJsonObject.add(key, JsonParser.parseString(value.toString()))
                save()
            }

        }
    }
}