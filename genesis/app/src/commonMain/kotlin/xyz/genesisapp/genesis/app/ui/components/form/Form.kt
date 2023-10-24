package xyz.genesisapp.genesis.app.ui.components.form

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

open class Form(val fields: List<FormItem<*>>, val onSubmitV: (Map<String, Any>.() -> Unit)?, val submitText: String) {
    @Composable
    open fun compose() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (field in fields) {
                field.compose()
            }
            Button(
                onClick = {
                    onSubmitV?.invoke(fields.associate { it.id to it.value.value!! })
                }
            ) {
                Text(submitText)
            }
        }

    }
}