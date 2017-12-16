import org.postgresql.Driver
import java.sql.Connection
import java.sql.DriverManager

class Injection {

    fun testInjection() {
        val searchQuery = "'me' OR 1=1"
        val connection = connectToDb()
        val statement = connection.createStatement()
        val result = statement.executeQuery(
                "SELECT * from students WHERE name=" + searchQuery)
        val columnsCount = result.metaData.columnCount
        repeat(columnsCount) {
            print("${result.metaData.getColumnName(it+1)}\t\t")
        }
        println()
        while (result.next()) {
            repeat(columnsCount) {
                print("${result.getString(result.metaData.getColumnName(it + 1))}\t\t")
            }
            println()
        }
        connection.close()
    }

    fun connectToDb(): Connection {
        DriverManager.registerDriver(Driver())
        val connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "password")
        return connection
    }

}