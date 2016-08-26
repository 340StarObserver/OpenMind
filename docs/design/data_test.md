## 数据库测试 -- 关于 文档结构 & 数据索引 的测试验证 ##


### 01. 查询用户的测试 ###

数据数量 : 200w  

查询计划 :  

        db.user_info.find({"_id":"1472194325byeuuxwxsnakihmb"}).limit(1).explain("allPlansExecution")  

查询目的 :  

        查出用户名为1472194325byeuuxwxsnakihmb的那个用户的信息  

计划结果 :  

        {
        	"queryPlanner" : {
        		"plannerVersion" : 1,
        		"namespace" : "openmind.user_info",
        		"indexFilterSet" : false,
        		"parsedQuery" : {
        			"_id" : {
        				"$eq" : "1472194325byeuuxwxsnakihmb"
        			}
        		},
        		"winningPlan" : {
        			"stage" : "IDHACK"
        		},
        		"rejectedPlans" : [ ]
        	},
        	"executionStats" : {
        		"executionSuccess" : true,
        		"nReturned" : 1,
        		"executionTimeMillis" : 17,
        		"totalKeysExamined" : 1,
        		"totalDocsExamined" : 1,
        		"executionStages" : {
        			"stage" : "IDHACK",
        			"nReturned" : 1,
        			"executionTimeMillisEstimate" : 10,
        			"works" : 2,
        			"advanced" : 1,
        			"needTime" : 0,
        			"needYield" : 0,
        			"saveState" : 0,
        			"restoreState" : 0,
        			"isEOF" : 1,
        			"invalidates" : 0,
        			"keysExamined" : 1,
        			"docsExamined" : 1
        		},
        		"allPlansExecution" : [ ]
        	},
        	"serverInfo" : {
        		"host" : "localhost.mshome.net",
        		"port" : 28001,
        		"version" : "3.2.4",
        		"gitVersion" : "e2ee9ffcf9f5a94fad76802e28cc978718bb7a30"
        	},
        	"ok" : 1
        }


### 02. 按时间分页查询项目概要的测试 ###

数据数量 : 200w  

查询计划 :  

        db.project_info.find({"pub_time":{$lte:1472198000}}).limit(5).explain("allPlansExecution")  

查询目的 :  

        以分页的方式，查出发布时间早于某时间戳的若干个项目的信息  

计划结果 :  

        {
        	"queryPlanner" : {
        		"plannerVersion" : 1,
        		"namespace" : "openmind.project_info",
        		"indexFilterSet" : false,
        		"parsedQuery" : {
        			"pub_time" : {
        				"$lte" : 1472198000
        			}
        		},
        		"winningPlan" : {
        			"stage" : "LIMIT",
        			"limitAmount" : 5,
        			"inputStage" : {
        				"stage" : "FETCH",
        				"inputStage" : {
        					"stage" : "IXSCAN",
        					"keyPattern" : {
        						"pub_time" : -1
        					},
        					"indexName" : "pub_time_-1",
        					"isMultiKey" : false,
        					"isUnique" : false,
        					"isSparse" : false,
        					"isPartial" : false,
        					"indexVersion" : 1,
        					"direction" : "forward",
        					"indexBounds" : {
        						"pub_time" : [
        							"[1472198000.0, -inf.0]"
        						]
        					}
        				}
        			}
        		},
        		"rejectedPlans" : [ ]
        	},
        	"executionStats" : {
        		"executionSuccess" : true,
        		"nReturned" : 5,
        		"executionTimeMillis" : 11,
        		"totalKeysExamined" : 5,
        		"totalDocsExamined" : 5,
        		"executionStages" : {
        			"stage" : "LIMIT",
        			"nReturned" : 5,
        			"executionTimeMillisEstimate" : 10,
        			"works" : 6,
        			"advanced" : 5,
        			"needTime" : 0,
        			"needYield" : 0,
        			"saveState" : 0,
        			"restoreState" : 0,
        			"isEOF" : 1,
        			"invalidates" : 0,
        			"limitAmount" : 5,
        			"inputStage" : {
        				"stage" : "FETCH",
        				"nReturned" : 5,
        				"executionTimeMillisEstimate" : 10,
        				"works" : 5,
        				"advanced" : 5,
        				"needTime" : 0,
        				"needYield" : 0,
        				"saveState" : 0,
        				"restoreState" : 0,
        				"isEOF" : 0,
        				"invalidates" : 0,
        				"docsExamined" : 5,
        				"alreadyHasObj" : 0,
        				"inputStage" : {
        					"stage" : "IXSCAN",
        					"nReturned" : 5,
        					"executionTimeMillisEstimate" : 0,
        					"works" : 5,
        					"advanced" : 5,
        					"needTime" : 0,
        					"needYield" : 0,
        					"saveState" : 0,
        					"restoreState" : 0,
        					"isEOF" : 0,
        					"invalidates" : 0,
        					"keyPattern" : {
        						"pub_time" : -1
        					},
        					"indexName" : "pub_time_-1",
        					"isMultiKey" : false,
        					"isUnique" : false,
        					"isSparse" : false,
        					"isPartial" : false,
        					"indexVersion" : 1,
        					"direction" : "forward",
        					"indexBounds" : {
        						"pub_time" : [
        							"[1472198000.0, -inf.0]"
        						]
        					},
        					"keysExamined" : 5,
        					"dupsTested" : 0,
        					"dupsDropped" : 0,
        					"seenInvalidated" : 0
        				}
        			}
        		},
        		"allPlansExecution" : [ ]
        	},
        	"serverInfo" : {
        		"host" : "localhost.mshome.net",
        		"port" : 28001,
        		"version" : "3.2.4",
        		"gitVersion" : "e2ee9ffcf9f5a94fad76802e28cc978718bb7a30"
        	},
        	"ok" : 1
        }


### 03. 查询个人活跃数据的测试 ###

数据数量 : 200w  

查询计划 :  

        db.active_info.find({"username":"1472200791jbtqjdjnohprkrtz","month":{$lte:201608}}).limit(3).explain("allPlansExecution")  

查询目的 :  

        以分页的方式，查出某个用户的某几个月的活跃数据  

计划结果 :  

        {
        	"queryPlanner" : {
        		"plannerVersion" : 1,
        		"namespace" : "openmind.active_info",
        		"indexFilterSet" : false,
        		"parsedQuery" : {
        			"$and" : [
        				{
        					"username" : {
        						"$eq" : "1472200791jbtqjdjnohprkrtz"
        					}
        				},
        				{
        					"month" : {
        						"$lte" : 201608
        					}
        				}
        			]
        		},
        		"winningPlan" : {
        			"stage" : "LIMIT",
        			"limitAmount" : 3,
        			"inputStage" : {
        				"stage" : "FETCH",
        				"inputStage" : {
        					"stage" : "IXSCAN",
        					"keyPattern" : {
        						"username" : 1,
        						"month" : -1
        					},
        					"indexName" : "username_1_month_-1",
        					"isMultiKey" : false,
        					"isUnique" : false,
        					"isSparse" : false,
        					"isPartial" : false,
        					"indexVersion" : 1,
        					"direction" : "forward",
        					"indexBounds" : {
        						"username" : [
        							"[\"1472200791jbtqjdjnohprkrtz\", \"1472200791jbtqjdjnohprkrtz\"]"
        						],
        						"month" : [
        							"[201608.0, -inf.0]"
        						]
        					}
        				}
        			}
        		},
        		"rejectedPlans" : [ ]
        	},
        	"executionStats" : {
        		"executionSuccess" : true,
        		"nReturned" : 3,
        		"executionTimeMillis" : 19,
        		"totalKeysExamined" : 3,
        		"totalDocsExamined" : 3,
        		"executionStages" : {
        			"stage" : "LIMIT",
        			"nReturned" : 3,
        			"executionTimeMillisEstimate" : 20,
        			"works" : 4,
        			"advanced" : 3,
        			"needTime" : 0,
        			"needYield" : 0,
        			"saveState" : 1,
        			"restoreState" : 1,
        			"isEOF" : 1,
        			"invalidates" : 0,
        			"limitAmount" : 3,
        			"inputStage" : {
        				"stage" : "FETCH",
        				"nReturned" : 3,
        				"executionTimeMillisEstimate" : 20,
        				"works" : 3,
        				"advanced" : 3,
        				"needTime" : 0,
        				"needYield" : 0,
        				"saveState" : 1,
        				"restoreState" : 1,
        				"isEOF" : 0,
        				"invalidates" : 0,
        				"docsExamined" : 3,
        				"alreadyHasObj" : 0,
        				"inputStage" : {
        					"stage" : "IXSCAN",
        					"nReturned" : 3,
        					"executionTimeMillisEstimate" : 20,
        					"works" : 3,
        					"advanced" : 3,
        					"needTime" : 0,
        					"needYield" : 0,
        					"saveState" : 1,
        					"restoreState" : 1,
        					"isEOF" : 0,
        					"invalidates" : 0,
        					"keyPattern" : {
        						"username" : 1,
        						"month" : -1
        					},
        					"indexName" : "username_1_month_-1",
        					"isMultiKey" : false,
        					"isUnique" : false,
        					"isSparse" : false,
        					"isPartial" : false,
        					"indexVersion" : 1,
        					"direction" : "forward",
        					"indexBounds" : {
        						"username" : [
        							"[\"1472200791jbtqjdjnohprkrtz\", \"1472200791jbtqjdjnohprkrtz\"]"
        						],
        						"month" : [
        							"[201608.0, -inf.0]"
        						]
        					},
        					"keysExamined" : 3,
        					"dupsTested" : 0,
        					"dupsDropped" : 0,
        					"seenInvalidated" : 0
        				}
        			}
        		},
        		"allPlansExecution" : [ ]
        	},
        	"serverInfo" : {
        		"host" : "localhost.mshome.net",
        		"port" : 28001,
        		"version" : "3.2.4",
        		"gitVersion" : "e2ee9ffcf9f5a94fad76802e28cc978718bb7a30"
        	},
        	"ok" : 1
        }


### 04. 查询与我相关的消息的测试 ###

数据数量 : 200w  

查询计划 :  

        db.associate_info.find({"username":"1472202864speuuwsbutsmqjem","time":{$lte:1472203589}}).limit(5).explain("allPlansExecution")  

查询目的 :

        以分页的方式，查出和某人相关的消息  

计划结果 :

        {
        	"queryPlanner" : {
        		"plannerVersion" : 1,
        		"namespace" : "openmind.associate_info",
        		"indexFilterSet" : false,
        		"parsedQuery" : {
        			"$and" : [
        				{
        					"username" : {
        						"$eq" : "1472202864speuuwsbutsmqjem"
        					}
        				},
        				{
        					"time" : {
        						"$lte" : 1472203589
        					}
        				}
        			]
        		},
        		"winningPlan" : {
        			"stage" : "LIMIT",
        			"limitAmount" : 5,
        			"inputStage" : {
        				"stage" : "FETCH",
        				"inputStage" : {
        					"stage" : "IXSCAN",
        					"keyPattern" : {
        						"username" : 1,
        						"time" : -1
        					},
        					"indexName" : "username_1_time_-1",
        					"isMultiKey" : false,
        					"isUnique" : false,
        					"isSparse" : false,
        					"isPartial" : false,
        					"indexVersion" : 1,
        					"direction" : "forward",
        					"indexBounds" : {
        						"username" : [
        							"[\"1472202864speuuwsbutsmqjem\", \"1472202864speuuwsbutsmqjem\"]"
        						],
        						"time" : [
        							"[1472203589.0, -inf.0]"
        						]
        					}
        				}
        			}
        		},
        		"rejectedPlans" : [ ]
        	},
        	"executionStats" : {
        		"executionSuccess" : true,
        		"nReturned" : 5,
        		"executionTimeMillis" : 16,
        		"totalKeysExamined" : 5,
        		"totalDocsExamined" : 5,
        		"executionStages" : {
        			"stage" : "LIMIT",
        			"nReturned" : 5,
        			"executionTimeMillisEstimate" : 10,
        			"works" : 6,
        			"advanced" : 5,
        			"needTime" : 0,
        			"needYield" : 0,
        			"saveState" : 0,
        			"restoreState" : 0,
        			"isEOF" : 1,
        			"invalidates" : 0,
        			"limitAmount" : 5,
        			"inputStage" : {
        				"stage" : "FETCH",
        				"nReturned" : 5,
        				"executionTimeMillisEstimate" : 10,
        				"works" : 5,
        				"advanced" : 5,
        				"needTime" : 0,
        				"needYield" : 0,
        				"saveState" : 0,
        				"restoreState" : 0,
        				"isEOF" : 0,
        				"invalidates" : 0,
        				"docsExamined" : 5,
        				"alreadyHasObj" : 0,
        				"inputStage" : {
        					"stage" : "IXSCAN",
        					"nReturned" : 5,
        					"executionTimeMillisEstimate" : 10,
        					"works" : 5,
        					"advanced" : 5,
        					"needTime" : 0,
        					"needYield" : 0,
        					"saveState" : 0,
        					"restoreState" : 0,
        					"isEOF" : 0,
        					"invalidates" : 0,
        					"keyPattern" : {
        						"username" : 1,
        						"time" : -1
        					},
        					"indexName" : "username_1_time_-1",
        					"isMultiKey" : false,
        					"isUnique" : false,
        					"isSparse" : false,
        					"isPartial" : false,
        					"indexVersion" : 1,
        					"direction" : "forward",
        					"indexBounds" : {
        						"username" : [
        							"[\"1472202864speuuwsbutsmqjem\", \"1472202864speuuwsbutsmqjem\"]"
        						],
        						"time" : [
        							"[1472203589.0, -inf.0]"
        						]
        					},
        					"keysExamined" : 5,
        					"dupsTested" : 0,
        					"dupsDropped" : 0,
        					"seenInvalidated" : 0
        				}
        			}
        		},
        		"allPlansExecution" : [ ]
        	},
        	"serverInfo" : {
        		"host" : "localhost.mshome.net",
        		"port" : 28001,
        		"version" : "3.2.4",
        		"gitVersion" : "e2ee9ffcf9f5a94fad76802e28cc978718bb7a30"
        	},
        	"ok" : 1
        }


### 05. 当前库的状态 ###

        {
        	"db" : "openmind",
        	"collections" : 5,
        	"objects" : 8016297,
        	"avgObjSize" : 184.07687489622703,
        	"dataSize" : 1475614900,
        	"storageSize" : 443445248,
        	"numExtents" : 0,
        	"indexes" : 7,
        	"indexSize" : 156647424,
        	"ok" : 1
        }


### 06. 最近15分钟的服务情况 ###

        {
        	"host" : "localhost.mshome.net:28001",
        	"advisoryHostFQDNs" : [ ],
        	"version" : "3.2.4",
        	"process" : "mongod",
        	"pid" : NumberLong(7911),
        	"uptime" : 9416,
        	"uptimeMillis" : NumberLong(9415883),
        	"uptimeEstimate" : 9306,
        	"localTime" : ISODate("2016-08-26T09:24:12.789Z"),
        	"asserts" : {
        		"regular" : 0,
        		"warning" : 0,
        		"msg" : 0,
        		"user" : 26,
        		"rollovers" : 0
        	},
        	"connections" : {
        		"current" : 1,
        		"available" : 818,
        		"totalCreated" : NumberLong(35)
        	},
        	"extra_info" : {
        		"note" : "fields vary by platform",
        		"heap_usage_bytes" : 582767232,
        		"page_faults" : 0
        	},
        	"globalLock" : {
        		"totalTime" : NumberLong("9415890000"),
        		"currentQueue" : {
        			"total" : 0,
        			"readers" : 0,
        			"writers" : 0
        		},
        		"activeClients" : {
        			"total" : 8,
        			"readers" : 0,
        			"writers" : 0
        		}
        	},
        	"locks" : {
        		"Global" : {
        			"acquireCount" : {
        				"r" : NumberLong(167921),
        				"w" : NumberLong(133073),
        				"W" : NumberLong(4)
        			}
        		},
        		"Database" : {
        			"acquireCount" : {
        				"r" : NumberLong(17406),
        				"w" : NumberLong(133059),
        				"R" : NumberLong(16),
        				"W" : NumberLong(14)
        			}
        		},
        		"Collection" : {
        			"acquireCount" : {
        				"r" : NumberLong(18041),
        				"w" : NumberLong(133062),
        				"W" : NumberLong(4)
        			}
        		},
        		"Metadata" : {
        			"acquireCount" : {
        				"w" : NumberLong(1),
        				"W" : NumberLong(9)
        			}
        		}
        	},
        	"network" : {
        		"bytesIn" : NumberLong(1551357531),
        		"bytesOut" : NumberLong(810720),
        		"numRequests" : NumberLong(9133)
        	},
        	"opcounters" : {
        		"insert" : 8216806,
        		"query" : 31,
        		"update" : 0,
        		"delete" : 4,
        		"getmore" : 0,
        		"command" : 844
        	},
        	"opcountersRepl" : {
        		"insert" : 0,
        		"query" : 0,
        		"update" : 0,
        		"delete" : 0,
        		"getmore" : 0,
        		"command" : 0
        	},
        	"storageEngine" : {
        		"name" : "wiredTiger",
        		"supportsCommittedReads" : true
        	},
        	"tcmalloc" : {
        		"generic" : {
        			"current_allocated_bytes" : 582768768,
        			"heap_size" : 975077376
        		},
        		"tcmalloc" : {
        			"pageheap_free_bytes" : 190898176,
        			"pageheap_unmapped_bytes" : 0,
        			"max_total_thread_cache_bytes" : NumberLong(1073741824),
        			"current_total_thread_cache_bytes" : 5678504,
        			"central_cache_free_bytes" : 188321096,
        			"transfer_cache_free_bytes" : 7410832,
        			"thread_cache_free_bytes" : 5678504,
        			"aggressive_memory_decommit" : 0
        		},
        		"formattedString" : "------------------------------------------------\nMALLOC:      582768768 (  555.8 MiB) Bytes in use by application\nMALLOC: +    190898176 (  182.1 MiB) Bytes in page heap freelist\nMALLOC: +    188321096 (  179.6 MiB) Bytes in central cache freelist\nMALLOC: +      7410832 (    7.1 MiB) Bytes in transfer cache freelist\nMALLOC: +      5678504 (    5.4 MiB) Bytes in thread cache freelists\nMALLOC: +      4845728 (    4.6 MiB) Bytes in malloc metadata\nMALLOC:   ------------\nMALLOC: =    979923104 (  934.5 MiB) Actual memory used (physical + swap)\nMALLOC: +            0 (    0.0 MiB) Bytes released to OS (aka unmapped)\nMALLOC:   ------------\nMALLOC: =    979923104 (  934.5 MiB) Virtual address space used\nMALLOC:\nMALLOC:          45520              Spans in use\nMALLOC:             16              Thread heaps in use\nMALLOC:           8192              Tcmalloc page size\n------------------------------------------------\nCall ReleaseFreeMemory() to release freelist memory to the OS (via madvise()).\nBytes released to the OS take up virtual address space but no physical memory.\n"
        	},
        	"wiredTiger" : {
        		"uri" : "statistics:",
        		"LSM" : {
        			"sleep for LSM checkpoint throttle" : 0,
        			"sleep for LSM merge throttle" : 0,
        			"rows merged in an LSM tree" : 0,
        			"application work units currently queued" : 0,
        			"merge work units currently queued" : 0,
        			"tree queue hit maximum" : 0,
        			"switch work units currently queued" : 0,
        			"tree maintenance operations scheduled" : 0,
        			"tree maintenance operations discarded" : 0,
        			"tree maintenance operations executed" : 0
        		},
        		"async" : {
        			"number of allocation state races" : 0,
        			"number of operation slots viewed for allocation" : 0,
        			"current work queue length" : 0,
        			"number of flush calls" : 0,
        			"number of times operation allocation failed" : 0,
        			"maximum work queue length" : 0,
        			"number of times worker found no work" : 0,
        			"total allocations" : 0,
        			"total compact calls" : 0,
        			"total insert calls" : 0,
        			"total remove calls" : 0,
        			"total search calls" : 0,
        			"total update calls" : 0
        		},
        		"block-manager" : {
        			"mapped bytes read" : 0,
        			"bytes read" : 21315584,
        			"bytes written" : 642150400,
        			"mapped blocks read" : 0,
        			"blocks pre-loaded" : 13,
        			"blocks read" : 1931,
        			"blocks written" : 71227
        		},
        		"cache" : {
        			"tracked dirty bytes in the cache" : 0,
        			"tracked bytes belonging to internal pages in the cache" : 3230224,
        			"bytes currently in the cache" : 534565485,
        			"tracked bytes belonging to leaf pages in the cache" : 531335261,
        			"maximum bytes configured" : 1073741824,
        			"tracked bytes belonging to overflow pages in the cache" : 0,
        			"bytes read into cache" : 22628676,
        			"bytes written from cache" : 1759279670,
        			"pages evicted by application threads" : 0,
        			"checkpoint blocked page eviction" : 0,
        			"unmodified pages evicted" : 25,
        			"page split during eviction deepened the tree" : 2,
        			"modified pages evicted" : 1129,
        			"pages selected for eviction unable to be evicted" : 2,
        			"pages evicted because they exceeded the in-memory maximum" : 18,
        			"pages evicted because they had chains of deleted items" : 469,
        			"failed eviction of pages that exceeded the in-memory maximum" : 0,
        			"hazard pointer blocked page eviction" : 0,
        			"internal pages evicted" : 44,
        			"maximum page size at eviction" : 9447751,
        			"eviction server candidate queue empty when topping up" : 471,
        			"eviction server candidate queue not empty when topping up" : 4,
        			"eviction server evicting pages" : 0,
        			"eviction server populating queue, but not evicting pages" : 474,
        			"eviction server unable to reach eviction goal" : 0,
        			"internal pages split during eviction" : 2,
        			"leaf pages split during eviction" : 532,
        			"pages walked for eviction" : 31856929,
        			"eviction worker thread evicting pages" : 1131,
        			"in-memory page splits" : 468,
        			"in-memory page passed criteria to be split" : 936,
        			"lookaside table insert calls" : 0,
        			"lookaside table remove calls" : 0,
        			"percentage overhead" : 8,
        			"tracked dirty pages in the cache" : 0,
        			"pages currently held in the cache" : 1340,
        			"pages read into cache" : 1793,
        			"pages read into cache requiring lookaside entries" : 0,
        			"pages written from cache" : 70942,
        			"page written requiring lookaside records" : 0,
        			"pages written requiring in-memory restoration" : 0
        		},
        		"connection" : {
        			"pthread mutex condition wait calls" : 4013313,
        			"files currently open" : 26,
        			"memory allocations" : 46218334,
        			"memory frees" : 41256344,
        			"memory re-allocations" : 21649,
        			"total read I/Os" : 3215,
        			"pthread mutex shared lock read-lock calls" : 24036,
        			"pthread mutex shared lock write-lock calls" : 9667,
        			"total write I/Os" : 95101
        		},
        		"cursor" : {
        			"cursor create calls" : 194,
        			"cursor insert calls" : 22450125,
        			"cursor next calls" : 228953,
        			"cursor prev calls" : 14,
        			"cursor remove calls" : 401535,
        			"cursor reset calls" : 23259927,
        			"cursor restarted searches" : 0,
        			"cursor search calls" : 406522,
        			"cursor search near calls" : 202348,
        			"truncate calls" : 0,
        			"cursor update calls" : 0
        		},
        		"data-handle" : {
        			"connection data handles currently active" : 23,
        			"session dhandles swept" : 0,
        			"session sweep attempts" : 84,
        			"connection sweep dhandles closed" : 0,
        			"connection sweep candidate became referenced" : 0,
        			"connection sweep dhandles removed from hash list" : 104,
        			"connection sweep time-of-death sets" : 152,
        			"connection sweeps" : 941
        		},
        		"log" : {
        			"total log buffer size" : 33554432,
        			"log bytes of payload data" : 2175040130,
        			"log bytes written" : 2641303552,
        			"yields waiting for previous log file close" : 0,
        			"total size of compressed records" : 96219,
        			"total in-memory size of compressed records" : 332921,
        			"log records too small to compress" : 200589,
        			"log records not compressed" : 8216798,
        			"log records compressed" : 111,
        			"log flush operations" : 92076,
        			"maximum log file size" : 104857600,
        			"pre-allocated log files prepared" : 27,
        			"number of pre-allocated log files to create" : 2,
        			"pre-allocated log files not ready and missed" : 1,
        			"pre-allocated log files used" : 25,
        			"log release advances write LSN" : 48,
        			"records processed by log scan" : 11,
        			"log scan records requiring two reads" : 2,
        			"log scan operations" : 5,
        			"consolidated slot closures" : 298654,
        			"written slots coalesced" : 0,
        			"logging bytes consolidated" : 2641299968,
        			"consolidated slot joins" : 8417498,
        			"consolidated slot join races" : 4474992,
        			"busy returns attempting to switch slots" : 845,
        			"consolidated slot join transitions" : 298654,
        			"consolidated slot unbuffered writes" : 0,
        			"log sync operations" : 3366,
        			"log sync_dir operations" : 26,
        			"log server thread advances write LSN" : 23752,
        			"log write operations" : 8417498,
        			"log files manually zero-filled" : 0
        		},
        		"reconciliation" : {
        			"pages deleted" : 300,
        			"fast-path pages deleted" : 0,
        			"page reconciliation calls" : 3266,
        			"page reconciliation calls for eviction" : 315,
        			"split bytes currently awaiting free" : 29482,
        			"split objects currently awaiting free" : 9
        		},
        		"session" : {
        			"open cursor count" : 29,
        			"open session count" : 16
        		},
        		"thread-yield" : {
        			"page acquire busy blocked" : 0,
        			"page acquire eviction blocked" : 0,
        			"page acquire locked blocked" : 246,
        			"page acquire read blocked" : 0,
        			"page acquire time sleeping (usecs)" : 884000
        		},
        		"transaction" : {
        			"transaction begins" : 8419737,
        			"transaction checkpoints" : 157,
        			"transaction checkpoint generation" : 157,
        			"transaction checkpoint currently running" : 0,
        			"transaction checkpoint max time (msecs)" : 1169,
        			"transaction checkpoint min time (msecs)" : 1,
        			"transaction checkpoint most recent time (msecs)" : 2,
        			"transaction checkpoint total time (msecs)" : 12478,
        			"transactions committed" : 8417380,
        			"transaction failures due to cache overflow" : 0,
        			"transaction range of IDs currently pinned by a checkpoint" : 0,
        			"transaction range of IDs currently pinned" : 0,
        			"transaction range of IDs currently pinned by named snapshots" : 0,
        			"transactions rolled back" : 2357,
        			"number of named snapshots created" : 0,
        			"number of named snapshots dropped" : 0,
        			"transaction sync calls" : 0
        		},
        		"concurrentTransactions" : {
        			"write" : {
        				"out" : 0,
        				"available" : 128,
        				"totalTickets" : 128
        			},
        			"read" : {
        				"out" : 0,
        				"available" : 128,
        				"totalTickets" : 128
        			}
        		}
        	},
        	"writeBacksQueued" : false,
        	"mem" : {
        		"bits" : 64,
        		"resident" : 947,
        		"virtual" : 1148,
        		"supported" : true,
        		"mapped" : 0,
        		"mappedWithJournal" : 0
        	},
        	"metrics" : {
        		"commands" : {
        			"buildInfo" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(9)
        			},
        			"collStats" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(1)
        			},
        			"count" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(11)
        			},
        			"createIndexes" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(3)
        			},
        			"dbStats" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(6)
        			},
        			"delete" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(4)
        			},
        			"explain" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(21)
        			},
        			"find" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(29)
        			},
        			"insert" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(8231)
        			},
        			"isMaster" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(672)
        			},
        			"listCollections" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(8)
        			},
        			"listIndexes" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(13)
        			},
        			"replSetGetStatus" : {
        				"failed" : NumberLong(6),
        				"total" : NumberLong(6)
        			},
        			"saslContinue" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(54)
        			},
        			"saslStart" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(27)
        			},
        			"serverStatus" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(3)
        			},
        			"usersInfo" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(1)
        			},
        			"whatsmyuri" : {
        				"failed" : NumberLong(0),
        				"total" : NumberLong(9)
        			}
        		},
        		"cursor" : {
        			"timedOut" : NumberLong(0),
        			"open" : {
        				"noTimeout" : NumberLong(0),
        				"pinned" : NumberLong(0),
        				"total" : NumberLong(0)
        			}
        		},
        		"document" : {
        			"deleted" : NumberLong(200518),
        			"inserted" : NumberLong(16433612),
        			"returned" : NumberLong(114),
        			"updated" : NumberLong(0)
        		},
        		"getLastError" : {
        			"wtime" : {
        				"num" : 0,
        				"totalMillis" : 0
        			},
        			"wtimeouts" : NumberLong(0)
        		},
        		"operation" : {
        			"fastmod" : NumberLong(0),
        			"idhack" : NumberLong(2),
        			"scanAndOrder" : NumberLong(2),
        			"writeConflicts" : NumberLong(0)
        		},
        		"queryExecutor" : {
        			"scanned" : NumberLong(25),
        			"scannedObjects" : NumberLong(26226)
        		},
        		"record" : {
        			"moves" : NumberLong(0)
        		},
        		"repl" : {
        			"executor" : {
        				"counters" : {
        					"eventCreated" : 0,
        					"eventWait" : 0,
        					"cancels" : 0,
        					"waits" : 0,
        					"scheduledNetCmd" : 0,
        					"scheduledDBWork" : 0,
        					"scheduledXclWork" : 0,
        					"scheduledWorkAt" : 0,
        					"scheduledWork" : 0,
        					"schedulingFailures" : 0
        				},
        				"queues" : {
        					"networkInProgress" : 0,
        					"dbWorkInProgress" : 0,
        					"exclusiveInProgress" : 0,
        					"sleepers" : 0,
        					"ready" : 0,
        					"free" : 0
        				},
        				"unsignaledEvents" : 0,
        				"eventWaiters" : 0,
        				"shuttingDown" : false,
        				"networkInterface" : "NetworkInterfaceASIO inShutdown: 0"
        			},
        			"apply" : {
        				"batches" : {
        					"num" : 0,
        					"totalMillis" : 0
        				},
        				"ops" : NumberLong(0)
        			},
        			"buffer" : {
        				"count" : NumberLong(0),
        				"maxSizeBytes" : 268435456,
        				"sizeBytes" : NumberLong(0)
        			},
        			"network" : {
        				"bytes" : NumberLong(0),
        				"getmores" : {
        					"num" : 0,
        					"totalMillis" : 0
        				},
        				"ops" : NumberLong(0),
        				"readersCreated" : NumberLong(0)
        			},
        			"preload" : {
        				"docs" : {
        					"num" : 0,
        					"totalMillis" : 0
        				},
        				"indexes" : {
        					"num" : 0,
        					"totalMillis" : 0
        				}
        			}
        		},
        		"storage" : {
        			"freelist" : {
        				"search" : {
        					"bucketExhausted" : NumberLong(0),
        					"requests" : NumberLong(0),
        					"scanned" : NumberLong(0)
        				}
        			}
        		},
        		"ttl" : {
        			"deletedDocuments" : NumberLong(0),
        			"passes" : NumberLong(156)
        		}
        	},
        	"ok" : 1
        }


### 07. 慢日志分析 ###

        db.system.profile.find({op:"query"}).limit(5).pretty()  
        
        查看查询操作的慢日志，未出现查询的慢日志  
        说明当前的存储方案，对目前应用所需要的查询是支持得比较好的  
        写性能需要另外测试  
        
