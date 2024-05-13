package com.example.realtimedatabase.a

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealTimeDbRepository1(private val db: DatabaseReference) : RealTimeRepository1 {
    override fun insert1(item: RealTimeModelResponse1.RealTimeItems1): Flow<ResultState1<String>> =
        callbackFlow {
            trySend(ResultState1.Loading)

            db.push().setValue(
                item
            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(ResultState1.Success("Data Insert SuccessFully"))
                }
            }.addOnFailureListener {
                trySend(ResultState1.Error(it))
            }
            awaitClose { close() }

        }

    override fun getItems1(): Flow<ResultState1<List<RealTimeModelResponse1>>> = callbackFlow {
        trySend(ResultState1.Loading)
        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children.map {
                    RealTimeModelResponse1(
                        it.getValue(RealTimeModelResponse1.RealTimeItems1::class.java),
                        key = it.key
                    )

                }

                trySend(ResultState1.Success(items))

            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState1.Error(error.toException()))
            }

        }

        db.addValueEventListener(valueEvent)
        awaitClose {
            db.removeEventListener(valueEvent)
            close()
        }
    }

    override fun delete1(key: String): Flow<ResultState1<String>> = callbackFlow {
        trySend(ResultState1.Loading)
        db.child(key).removeValue()
            .addOnCompleteListener {
                trySend(ResultState1.Success("Deleted Successfully"))
            }.addOnFailureListener {
                trySend(ResultState1.Error(it))
            }
        awaitClose {
            close()
        }
    }

    override fun update1(res: RealTimeModelResponse1): Flow<ResultState1<String>> = callbackFlow {
        trySend(ResultState1.Loading)
        val map = HashMap<String, Any>()
        map["tittle"] = res.item?.tittle!!
        map["description"] = res.item?.description!!

        db.child(res.key!!).updateChildren(map).addOnCompleteListener {
            trySend(ResultState1.Success("Updated Successfully"))
        }.addOnFailureListener {
            trySend(ResultState1.Error(it))
        }

        awaitClose {
            close()
        }
    }
}