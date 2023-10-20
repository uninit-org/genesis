package xyz.genesisapp.common.preferences

import platform.Foundation.NSUserDefaults

actual class PreferencesManager : CommonMultiplatformPreferencesManager() {
    override fun preference(key: String, defaultValue: String): Preference<String> {
        return Preference(key, defaultValue, { key, defaultValue ->
            NSUserDefaults.standardUserDefaults.stringForKey(key) ?: defaultValue
        }, { value ->
            NSUserDefaults.standardUserDefaults.setObject(value, key)
        })
    }

    override fun preference(key: String, defaultValue: Int): Preference<Int> {

        return Preference(key, defaultValue, { key, defaultValue ->
            NSUserDefaults.standardUserDefaults.integerForKey(key).toInt() ?: defaultValue
        }, { value ->
            NSUserDefaults.standardUserDefaults.setInteger(value.toLong(), key)
        })
    }

    override fun preference(key: String, defaultValue: Long): Preference<Long> {

        return Preference(key, defaultValue, { key, defaultValue ->
            NSUserDefaults.standardUserDefaults.integerForKey(key).toLong() ?: defaultValue
        }, { value ->
            NSUserDefaults.standardUserDefaults.setInteger(value, key)
        })

    }

    override fun preference(key: String, defaultValue: Float): Preference<Float> {
        return Preference(key, defaultValue, { key, defaultValue ->
            NSUserDefaults.standardUserDefaults.floatForKey(key).toFloat() ?: defaultValue
        }, { value ->
            NSUserDefaults.standardUserDefaults.setFloat(value, key)
        })
    }

    override fun preference(key: String, defaultValue: Boolean): Preference<Boolean> {
        return Preference(key, defaultValue, { key, defaultValue ->
            NSUserDefaults.standardUserDefaults.boolForKey(key) ?: defaultValue
        }, { value ->
            NSUserDefaults.standardUserDefaults.setBool(value, key)
        })
    }

    override fun preference(key: String, defaultValue: Set<String>): Preference<Set<String>> {
        return Preference(key, defaultValue, { key, defaultValue ->
            (NSUserDefaults.standardUserDefaults.stringArrayForKey(key) as List<String>)?.toSet()
                ?: defaultValue
        }, { value ->
            NSUserDefaults.standardUserDefaults.setObject(value, key)
        })
    }
}