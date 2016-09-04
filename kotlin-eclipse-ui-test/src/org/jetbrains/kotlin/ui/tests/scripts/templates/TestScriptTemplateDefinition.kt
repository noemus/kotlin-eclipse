package org.jetbrains.kotlin.ui.tests.scripts.templates

import org.jetbrains.kotlin.script.KotlinScriptExternalDependencies
import org.jetbrains.kotlin.script.ScriptContents
import org.jetbrains.kotlin.script.ScriptDependenciesResolver
import org.jetbrains.kotlin.script.ScriptDependenciesResolver.ReportSeverity
import org.jetbrains.kotlin.script.ScriptTemplateDefinition
import java.io.File
import java.util.concurrent.Future
import org.jetbrains.kotlin.script.asFuture

@ScriptTemplateDefinition(
        resolver = TestKotlinScriptResolver::class,
        scriptFilePattern = "sample.testDef.kts"
)
open class TestScriptTemplateDefinition(val testNameParam: String, val secondParam: Int, val thirdParam: Int = 10) {
    fun doSomething() {
    }

    fun callFromBase(y: Int) {
        println(y)
    }
}

fun TestScriptTemplateDefinition.testExtension(x: Int): String {
    return x.toString()
}

class TestKotlinScriptResolver : ScriptDependenciesResolver {
    override fun resolve(script: ScriptContents,
                         environment: Map<String, Any?>?,
                         report: (ReportSeverity, String, ScriptContents.Position?) -> Unit,
                         previousDependencies: KotlinScriptExternalDependencies?): Future<KotlinScriptExternalDependencies?> {
        return TestScriptExternalDependencies.asFuture()
    }
}

object TestScriptExternalDependencies : KotlinScriptExternalDependencies {
    override val classpath: Iterable<File>
        get() = listOf()

    override val sources: Iterable<File>
        get() = listOf()

    override val imports: Iterable<String>
        get() = listOf(
                "java.io.File",
                "java.util.concurrent.*",
                "org.jetbrains.kotlin.ui.tests.scripts.templates.*")
}