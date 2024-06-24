CREATE KEYSPACE IF NOT EXISTS mykeyspace
    WITH replication = {'class': 'NetworkTopologyStrategy', 'replication_factor' : 3}
    AND durable_writes = true;

CREATE TABLE IF NOT EXISTS mykeyspace.monkeySpecies (
    species text PRIMARY KEY,
    common_name text,
    population bigint,
    average_size int);