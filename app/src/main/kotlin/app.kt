val cleanUp:Regex = """[^.\d-+*\/]""".toRegex()
val mulDiv = """((?:\+-)?[.\d]+)([*\/])((?:\+-)?[.\d]+)""".toRegex()
var paren = """\(([^()]*)\)""".toRegex()

fun ex(v:String):Double =
    v.replace(cleanUp, "").replace("-", "+-").replace(mulDiv) {
        val(_, left, op, right) = it.groupValues
        var l = left.replace("+", "").toDouble()
        var r = right.replace("+", "").toDouble()
        "${if(op == "*") l * r else l / r}"
                .replace("-", "+-")
    }.split('+').fold(0.0){sum, v->
        sum + if(v.isBlank()) 0.0 else v.toDouble()
    }

fun calc(v:String):Double {
    var r = v
    while(paren.containsMatchIn(r)) r = r.replace(paren){"${ex(it.groupValues[1])}"}
    return ex(r)
}