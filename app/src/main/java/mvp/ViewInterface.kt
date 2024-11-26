package mvp

interface ViewInterface {
    fun setValues(vals:List<String>)
    fun onError(message: String)
}