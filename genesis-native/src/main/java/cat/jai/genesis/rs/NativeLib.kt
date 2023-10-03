package cat.jai.genesis.rs

class NativeLib {

    /**
     * A native method that is implemented by the 'rs' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'rs' library on application startup.
        init {
            System.loadLibrary("rs")
        }
    }
}