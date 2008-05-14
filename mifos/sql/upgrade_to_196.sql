UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Partial application' WHERE ACCOUNT_STATE_ID = 1;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Application pending approval' WHERE ACCOUNT_STATE_ID = 2;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Application approved' WHERE ACCOUNT_STATE_ID = 3;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Disbursed to loan officer' WHERE ACCOUNT_STATE_ID = 4;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Active in good standing' WHERE ACCOUNT_STATE_ID = 5;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Closed - obligation met' WHERE ACCOUNT_STATE_ID = 6;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Closed - written off' WHERE ACCOUNT_STATE_ID = 7;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Closed - rescheduled' WHERE ACCOUNT_STATE_ID = 8;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Active in bad standing' WHERE ACCOUNT_STATE_ID = 9;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Canceled' WHERE ACCOUNT_STATE_ID = 10;

UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Partial application' WHERE ACCOUNT_STATE_ID = 13;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Application pending approval' WHERE ACCOUNT_STATE_ID = 14;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Canceled' WHERE ACCOUNT_STATE_ID = 15;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Active' WHERE ACCOUNT_STATE_ID = 16;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Closed' WHERE ACCOUNT_STATE_ID = 17;
UPDATE ACCOUNT_STATE SET STATUS_DESCRIPTION = 'Inactive' WHERE ACCOUNT_STATE_ID = 18;

UPDATE DATABASE_VERSION SET DATABASE_VERSION = 196 WHERE DATABASE_VERSION=195;
