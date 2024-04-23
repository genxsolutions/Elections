package com.genxsol.elections.common.util
import com.genxsol.elections.api.Result


fun List<Result>.sortResultsByVotes(): List<Result> {
    return sortedByDescending { it.votes }
}