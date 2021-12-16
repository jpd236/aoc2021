fun main() {
    fun processPacket(initialData: String, addVersions: Boolean): Pair<String, Long> {
        var data = initialData
        val result: Long
        val version = data.substring(0..2).toLong(2)
        val typeId = data.substring(3..5).toInt(2)
        data = data.substring(6)
        if (typeId == 4) {
            var num = ""
            do {
                val continuationBit = data.substring(0..0)
                num += data.substring(1..4)
                data = data.substring(5)
            } while (continuationBit != "0")
            result = if (addVersions) {
                version
            } else {
                num.toLong(2)
            }
        } else {
            val lengthTypeId = data.substring(0..0)
            data = data.substring(1)
            val subPacketValues = mutableListOf<Long>()
            if (lengthTypeId == "0") {
                val length = data.substring(0..14).toInt(2)
                data = data.substring(15)
                val expectedRemainingData = data.substring(length)
                while (data != expectedRemainingData) {
                    val processed = processPacket(data, addVersions)
                    subPacketValues += processed.second
                    data = processed.first
                }
            } else {
                val subPackets = data.substring(0..10).toInt(2)
                data = data.substring(11)
                repeat(subPackets) {
                    val processed = processPacket(data, addVersions)
                    subPacketValues += processed.second
                    data = processed.first
                }
            }
            result = if (addVersions) {
                version + subPacketValues.sum()
            } else when (typeId) {
                0 -> subPacketValues.sum()
                1 -> subPacketValues.reduce(Long::times)
                2 -> subPacketValues.minOrNull()!!
                3 -> subPacketValues.maxOrNull()!!
                5 -> if (subPacketValues[0] > subPacketValues[1]) 1 else 0
                6 -> if (subPacketValues[0] < subPacketValues[1]) 1 else 0
                7 -> if (subPacketValues[0] == subPacketValues[1]) 1 else 0
                else -> error("Unknown type $typeId")
            }
        }
        return data to result
    }

    fun part1(data: String) {
        println(processPacket(data, addVersions = true).second)
    }

    fun part2(data: String) {
        println(processPacket(data, addVersions = false).second)
    }

    val input = readInput("Day16")
    var data = input[0].toBigInteger(16).toString(2)
    while (data.length % 4 != 0) {
        data = "0$data"
    }
    part1(data)
    part2(data)
}
