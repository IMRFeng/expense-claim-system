INSERT INTO User VALUES(1, 'administrator', 'QZTRcG7R9AjV4C1nJ3cBn01ThcdmqMbKisujFn02p7k=', true);
INSERT INTO User VALUES(2, 'victor', 'mb3gaK8tSe1/yLj6eavhOmBZ4NsyC7c0Wf2WYku0sz8=', true);
INSERT INTO User VALUES(3, 'peter', 'AmrZsUp0U7dIjaoMasvCWLFQb1LEQcfEZUdMGlZDlP8=', true);
INSERT INTO User VALUES(4, 'john', 'ltljLzY1ZMwwMlIUCc8iqFLyAy7sCZ7VlnwNAAzsYHo=', false);

INSERT INTO role VALUES(0, 'ROLE_SUPERMAN');
INSERT INTO role VALUES(1, 'ROLE_ADMIN');
INSERT INTO role VALUES(2, 'ROLE_USER');

INSERT INTO resources VALUES(1, '/dashboard', 'Dashboard page');
INSERT INTO resources VALUES(2, '/index', 'Index page');
INSERT INTO resources VALUES(3, '/getReceiptList', 'Get receipt list');
INSERT INTO resources VALUES(4, '/aboutUs', 'About Us page');
INSERT INTO resources VALUES(5, '/removeReceiptPic', 'Delete uploaded receipt image');
INSERT INTO resources VALUES(6, '/saveReceipt', 'Create receipt');
INSERT INTO resources VALUES(7, '/getReceiptImage/**', 'Get receipt image');
INSERT INTO resources VALUES(8, '/uploadReceiptPic', 'Upload receipt image');
INSERT INTO resources VALUES(9, '/setToClaimed', 'Set to claimed');
INSERT INTO resources VALUES(10, '/delete', 'Delete a receipt');

INSERT INTO user_role VALUES(1, 0);
INSERT INTO user_role VALUES(2, 1);
INSERT INTO user_role VALUES(3, 2);
INSERT INTO user_role VALUES(4, 2);

INSERT INTO role_resources VALUES(1, 1);
INSERT INTO role_resources VALUES(1, 2);
INSERT INTO role_resources VALUES(1, 3);
--INSERT INTO role_resources VALUES(1, 4);
INSERT INTO role_resources VALUES(1, 5);
INSERT INTO role_resources VALUES(1, 6);
INSERT INTO role_resources VALUES(1, 7);
INSERT INTO role_resources VALUES(1, 8);
INSERT INTO role_resources VALUES(1, 9);
INSERT INTO role_resources VALUES(1, 10);
INSERT INTO role_resources VALUES(2, 1);
INSERT INTO role_resources VALUES(2, 2);
INSERT INTO role_resources VALUES(2, 3);

INSERT INTO Receipt (date_stamp, description, category, cost, claim, file_name, pre_cost, dollar_type, image, thumbnail, rates) VALUES('2016-12-14', 'desc', 'Food', 10.2, 'No', '', 20.4, 'AUD', '','', 29.8);