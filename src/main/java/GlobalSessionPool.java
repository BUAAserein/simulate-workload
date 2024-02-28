/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.iotdb.rpc.IoTDBConnectionException;
import org.apache.iotdb.rpc.StatementExecutionException;
import org.apache.iotdb.session.pool.SessionPool;
import org.apache.iotdb.tsfile.file.metadata.enums.TSDataType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GlobalSessionPool {
  Logger logger = LoggerFactory.getLogger(GlobalSessionPool.class);
  private SessionPool sessionPool;

  private static final GlobalSessionPool instance = new GlobalSessionPool();

  private GlobalSessionPool() {}

  public static GlobalSessionPool getInstance() {
    return instance;
  }

  public void init() {
    sessionPool =
        new SessionPool(Configuration.dbIp, 6667, "root", "root", Configuration.clientCount);
  }

  public void executeNonQueryStatement(String sql)
      throws IoTDBConnectionException, StatementExecutionException {
    logger.info("Executing {}", sql);
    sessionPool.executeNonQueryStatement(sql);
  }

  public void insertRecords(
      List<String> deviceIds,
      List<Long> timestamps,
      List<List<String>> measurements,
      List<List<TSDataType>> types,
      List<List<Object>> values)
      throws IoTDBConnectionException, StatementExecutionException {
    sessionPool.insertRecords(deviceIds, timestamps, measurements, types, values);
  }
}
