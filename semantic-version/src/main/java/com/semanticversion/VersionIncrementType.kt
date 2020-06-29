package com.semanticversion

enum class VersionIncrementType {

    MAJOR {
        override fun increment(version: Version) {
            version.incrementMajor()
        }
    },
    MINOR {
        override fun increment(version: Version) {
            version.incrementMinor()
        }
    },
    PATCH {
        override fun increment(version: Version) {
            version.incrementPatch()
        }
    };

    abstract fun increment(version: Version)
}
