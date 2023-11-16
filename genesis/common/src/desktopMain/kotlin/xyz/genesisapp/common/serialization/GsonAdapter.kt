package xyz.genesisapp.common.serialization

import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import xyz.genesisapp.common.interfaces.IDynValue
import java.io.File
import kotlin.reflect.KClass
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
                var el = elem
                for (k in key.split(".")) {
                    el = el.asJsonObject[k] ?: return defaultValue
                }
                return el.asString
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
                var el = elem
                for (k in key.split(".")) {
                    el = el.asJsonObject[k] ?: return defaultValue
                }
                return el.asInt
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
                var el = elem
                for (k in key.split(".")) {
                    el = el.asJsonObject[k] ?: return defaultValue
                }
                return el.asLong
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
                var el = elem
                for (k in key.split(".")) {
                    el = el.asJsonObject[k] ?: return defaultValue
                }
                return el.asFloat
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
                var el = elem
                for (k in key.split(".")) {
                    el = el.asJsonObject[k] ?: return defaultValue
                }
                return el.asBoolean
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
                var el = elem
                for (k in key.split(".")) {
                    el = el.asJsonObject[k] ?: return defaultValue
                }
                return el.asJsonArray.map { it.asString }.toSet()
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: Set<String>) {
                elem.asJsonObject.remove(key)
                elem.asJsonObject.add(key, JsonParser.parseString(value.toString()))
                save()
            }

        }
    }
    @OptIn(InternalSerializationApi::class)
    fun <T : Any> value(key: String, defaultValue: T, klass: KClass<T>): IDynValue<T> {
        return object : IDynValue<T> {
            override fun getValue(thisRef: Any?, property: KProperty<*>): T {
                var el = elem
                for (k in key.split(".")) {
                    el = el.asJsonObject[k] ?: return defaultValue
                }
                return Json.decodeFromString(klass.serializer(), el.asString)
            }

            override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
                elem.asJsonObject.remove(key)
                elem.asJsonObject.add(key, JsonParser.parseString(Json.encodeToString(klass.serializer(), value)))
                save()
            }
        }
    }
}