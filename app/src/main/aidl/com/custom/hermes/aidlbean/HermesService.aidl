// HermesService.aidl
package com.custom.hermes.aidlbean;

// Declare any non-default types here with import statements
import com.custom.hermes.aidlbean.HermesRequest;
import com.custom.hermes.aidlbean.HermesResponse;
interface HermesService {
    HermesResponse send(in HermesRequest request);
}
