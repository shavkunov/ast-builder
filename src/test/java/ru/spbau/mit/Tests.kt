package ru.spbau.mit

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.file.Paths
import java.nio.file.Files



class Tests{
    val testPath = Paths.get("src", "test", "resources")!!

    @Test
    fun test() {
        val correct = testPath.resolve("correct")
        val test = testPath.resolve("tests")

        for (file in test.toFile().list()) {
            val byteOutputStream = ByteArrayOutputStream()
            val printStream = PrintStream(byteOutputStream)
            printAst(test.resolve(file).toAbsolutePath().toString(), printStream)

            val answer = correct.resolve(file).toFile()
            val answerContent = String(Files.readAllBytes(answer.toPath()))

            val actualOutput = String(byteOutputStream.toByteArray())

            assertEquals(answerContent, actualOutput)
        }
    }
}