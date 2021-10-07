package cgm.mokito

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MokitoTests {

    @InjectMocks
    private lateinit var mainClass: MainClass

    @Mock
    private lateinit var dependentClassOne: DatabaseDAO

    @Mock
    private lateinit var dependentClassTwo: NetworkDAO

    @Spy
    private lateinit var wordMap1: Map<String, String>

    @Mock
    private lateinit var mockedList: MutableList<String>

    @Spy
    private lateinit var spiedList: ArrayList<String>

    @InjectMocks
    var dic: MyDictionary = MyDictionary()

    @Test
    fun whenUseMockAnnotation_thenMockIsInjected() {
        mockedList.add("one")
        Mockito.verify(mockedList).add("one")
        assertEquals(0, mockedList.size)
        Mockito.`when`(mockedList.size).thenReturn(100)
        assertEquals(100, mockedList.size)
    }

    @Test
    fun whenNotUseMockAnnotation_thenCorrect() {
        val mockList: java.util.ArrayList<String> = Mockito.mock(ArrayList::class.java) as java.util.ArrayList<String>
        mockList.add("one")
        Mockito.verify(mockList).add("one")
        assertEquals(0, mockList.size)
        Mockito.`when`(mockList.size).thenReturn(100)
        assertEquals(100, mockList.size)
    }

    @Test
    fun whenNotUseSpyAnnotation_thenCorrect() {
        val spyList: MutableList<String> = Mockito.spy(ArrayList())
        spyList.add("one")
        spyList.add("two")
        Mockito.verify(spyList).add("one")
        Mockito.verify(spyList).add("two")
        assertEquals(2, spyList.size)
        Mockito.doReturn(100).`when`<List<String>>(spyList).size
        assertEquals(100, spyList.size)
    }

    @Test
    fun whenUseSpyAnnotation_thenSpyIsInjectedCorrectly() {
        spiedList.add("one")
        spiedList.add("two")
        Mockito.verify(spiedList).add("one")
        Mockito.verify(spiedList).add("two")
        assertEquals(2, spiedList.size)
        Mockito.doReturn(100).`when`<List<String>>(spiedList).size
        assertEquals(100, spiedList.size)
    }

    @Test
    fun whenUseInjectMocksAnnotation_thenCorrect() {
        Mockito.`when`(wordMap1["aWord"]).thenReturn("aMeaning")
        assertEquals("aMeaning", dic.getMeaning("aWord"))
    }

    @Test
    fun validateTest() {
        val saved: Boolean = mainClass.save("temp.txt")
        assertEquals(true, saved)
        Mockito.verify(dependentClassOne, times(1)).save("temp.txt");
        Mockito.verify(dependentClassTwo, times(1)).save("temp.txt");
    }
}

class MyDictionary {
    var wordMap: MutableMap<String, String>

    fun getMeaning(word: String): String? {
        return wordMap[word]
    }

    init {
        wordMap = HashMap()
    }
}

class MainClass {
    private var database: DatabaseDAO = DatabaseDAO()
    private var network: NetworkDAO = NetworkDAO()

    fun save(fileName: String?): Boolean {
        database.save(fileName)
        println("Saved in database in Main class")
        network.save(fileName)
        println("Saved in network in Main class")
        return true
    }
}

class DatabaseDAO {
    fun save(fileName: String?) {
        println("Saved in database")
    }
}

class NetworkDAO {
    fun save(fileName: String?) {
        println("Saved in network location")
    }
}