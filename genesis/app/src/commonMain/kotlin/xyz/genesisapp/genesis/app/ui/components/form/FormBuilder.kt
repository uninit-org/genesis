package xyz.genesisapp.genesis.app.ui.components.form

import androidx.compose.runtime.Composable

open class FormBuilder() {
    internal val fields: MutableList<FormItem<*>> = mutableListOf()
    var onSubmitV: (Map<String, Any>.() -> Unit)? = null
    var submitText: String = "Submit"
    fun text(
        id: String,
        label: String,
    ) {
        val field = FormItem.Text(id, label)
        fields.add(field)
    }

    fun password(
        id: String,
        label: String,
    ) {
        val field = FormItem.Password(id, label)
        fields.add(field)
    }

    fun switch(
        id: String,
        label: String,
    ) {
        val field = FormItem.Switch(id, label)
        fields.add(field)
    }

    fun select(
        id: String,
        label: String,
        options: List<String>,
    ) {
        val field = FormItem.MultiSelect(id, label, options)
        fields.add(field)
    }

    fun radio(
        id: String,
        label: String,
        options: List<String>,
    ) {
        val field = FormItem.Radio(id, label, options)
        fields.add(field)
    }

    fun number(
        id: String,
        label: String,
    ) {
        val field = FormItem.Number(id, label)
        fields.add(field)
    }

    fun separator(
        id: String,
        label: String,
    ) {
        val field = FormItem.Separator(id, label)
        fields.add(field)
    }
    open fun build(): Form {
        return Form(fields, onSubmitV, submitText)
    }

    open fun onSubmit(
        onSubmit: Map<String, Any>.() -> Unit,
    ) {
        onSubmitV = onSubmit
    }

    fun setSubmitText(
        submitText: String,
    ) {
        this.submitText = submitText
    }

    fun animatedToggleTwoSides(
        block: FormBuilder.() -> Unit, // m
    ) {

    }
}
fun buildForm(
    builder: FormBuilder.() -> Unit,
): Form {
    val formBuilder = FormBuilder()
    formBuilder.builder()
    return formBuilder.build()
}
@Composable
fun composeForm(
    builder: FormBuilder.() -> Unit
) {
    val formBuilder = FormBuilder()
    formBuilder.builder()
    val form = formBuilder.build()
    form.compose()
}