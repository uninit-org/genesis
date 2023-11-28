package uninit.genesis.app.ui.components.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import uninit.common.inverse

interface FormItem<T> {
    val id: String
    val label: String
    val value: MutableState<T>

    @Composable
    fun compose()
    open class Text(
        override val id: String,
        override val label: String,
    ) : FormItem<String> {
        override val value = mutableStateOf("")
        @Composable
        override fun compose() {
            TextField(
                value = value.value,
                onValueChange = {
                    value.value = it
                },
                label = {
                    Text(label)
                },
                singleLine = true,
                modifier = Modifier.padding(vertical = 8.dp))
        }
    }
    class Password(id: String, label: String) : Text(id, label) {
        @Composable
        override fun compose() {
            TextField(
                value = value.value,
                onValueChange = {
                    value.value = it
                },
                label = {
                    Text(label)
                },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.padding(vertical = 8.dp))
        }
    }

    class Switch(
        override val id: String,
        override val label: String,
    ) : FormItem<Boolean> {
        override val value = mutableStateOf(false)
        @Composable
        override fun compose() {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(label,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 1.dp))
                Switch(
                    checked = value.value,
                    onCheckedChange = {
                        value.value = it
                    },
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 1.dp)
                )
            }
        }
    }

    class MultiSelect(
        override val id: String,
        override val label: String,
        val options: List<String>,
    ) : FormItem<List<String>> {
        override val value = mutableStateOf(listOf(options.first()))
        @Composable
        override fun compose() {

            LazyColumn {
                items(options.size) { index ->
                    Checkbox(
                        checked = value.value.contains(options[index]),
                        onCheckedChange = {
                            if (it) {
                                value.value += options[index]
                            } else {
                                value.value -= options[index]
                            }
                        },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

        }
    }

    class Radio(
        override val id: String,
        override val label: String,
        val options: List<String>,
    ) : FormItem<String> {
        override val value = mutableStateOf(options.first())
        @Composable
        override fun compose() {

            LazyColumn {
                items(options.size) { index ->
                    RadioButton(
                        selected = value.value == options[index],
                        onClick = {
                            value.value = options[index]
                        },
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }

        }
    }

    class Number(
        override val id: String,
        override val label: String,
    ) : FormItem<Int> {
        override val value = mutableStateOf(0)
        @Composable
        override fun compose() {
            TextField(
                value = value.value.toString(),
                onValueChange = {
                    try {
                        if (it.isNotEmpty()) {
                            value.value = it.toInt()
                        }
                    } catch (_: Exception) {

                    }
                },
                label = {
                    Text(label)
                },
                singleLine = true,
                modifier = Modifier.padding(vertical = 8.dp))
        }
    }

    class Separator(
        override val id: String,
        override val label: String,
    ) : FormItem<Unit> {
        override val value = mutableStateOf(Unit)
        @Composable
        override fun compose() {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                if (label.isNotEmpty()) {
                    Divider(
                        thickness = 1.dp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 1.dp).weight(0.3f)
                    )
                    Text(label, Modifier)
                    Divider(
                        thickness = 1.dp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 1.dp).weight(0.3f)
                    )
                } else {
                    Divider(
                        thickness = 1.dp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 0.dp).fillMaxWidth()
                    )
                }
            }
        }
    }



    class TwoSidedForm(
        builder: TwoSidedFormBuilder.() -> Unit,
        override val id: String,
    ) : FormItem<MutableMap<String, MutableMap<String, Any>>> {
        private val ctx: TwoSidedFormContext = TwoSidedFormBuilder().apply(builder).build()
        override val label: String = ""
        override val value: MutableState<MutableMap<String, MutableMap<String, Any>>> = mutableStateOf(mutableMapOf())

        inner class TwoSidedFormBuilder(val mutableOpenState: MutableState<Boolean> = mutableStateOf(true)) {

            lateinit var left: TwoSidedFormSideForm
            lateinit var right: TwoSidedFormSideForm

            inner class TwoSidedFormSideBuilder(val id: String, val mutableOpenState: MutableState<Boolean>, val left: Boolean) : FormBuilder() {
                inner class HeadItem(override val label: String) : FormItem<Unit> {
                    override val id: String = ""
                    override val value: MutableState<Unit> = mutableStateOf(Unit)
                    @Composable
                    override fun compose() {
                        TODO()
                    }
                }
                inner class SwapItem(override val label: String, val swapMutableState: MutableState<Boolean>) : FormItem<Unit> {
                    override val id: String = ""
                    override val value: MutableState<Unit> = mutableStateOf(Unit)
                    @Composable
                    override fun compose() {
                        TODO()
                    }
                }
                fun head(
                    label: String,
                ) {
                    val field = HeadItem(label)
                    fields.add(field)
                }

                fun swap(
                    label: String,
                ) {
                    if (left) {
                        fields.add(
                            SwapItem(label, mutableOpenState)
                        )
                    } else {
                        fields.add(
                            SwapItem(label, mutableOpenState.inverse())
                        )
                    }
                }
                override fun onSubmit(onSubmit: Map<String, Any>.() -> Unit) {
                    error("Regular form onSubmit is not supported in twoSided form")
                }

                override fun build(): TwoSidedFormSideForm {
                    return TwoSidedFormSideForm(id, fields, submitText, onSubmitV)
                }
            }
            inner class TwoSidedFormSideForm(
                override val id: String,
                formItems: List<FormItem<*>>,
                submitText: String,
                val onSubmitTSV: (MutableMap<String, Any>.() -> Unit)? = null,

            ) : FormItem<MutableMap<String, Any>>, Form(formItems, {}, submitText) {
                override val label: String = ""
                override val value: MutableState<MutableMap<String, Any>> = mutableStateOf(mutableMapOf())

                @Composable
                override fun compose() {
                    TODO()
                }
            }

            fun left(
                id: String,
                builder: TwoSidedFormSideBuilder.() -> Unit,
            ) {
                left = TwoSidedFormSideBuilder(id, mutableOpenState, true).apply(builder).build()
            }
            fun right(
                id: String,
                builder: TwoSidedFormSideBuilder.() -> Unit,
            ) {
                right = TwoSidedFormSideBuilder(id, mutableOpenState, false).apply(builder).build()
            }


            fun build(): TwoSidedFormContext {
                TODO()
            }
        }
        inner class TwoSidedFormContext(
            val left: List<FormItem<*>>,
            val right: List<FormItem<*>>
        )
        @Composable
        override fun compose() {
            TODO()



        }
    }

}