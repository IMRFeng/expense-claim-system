INSERT INTO Fish (name, image_url, description, price, status) VALUES ('Pacific Halibut', 'http://i.istockimg.com/file_thumbview_approve/36248396/5/stock-photo-36248396-blackened-cajun-sea-bass.jpg', 'Everyones favorite white fish. We will cut it to the size you need and ship it.', 17.24, 'available');
INSERT INTO Fish (name, image_url, description, price, status) VALUES ('Lobster', 'http://i.istockimg.com/file_thumbview_approve/32135274/5/stock-photo-32135274-cooked-lobster.jpg', 'These tender, mouth-watering beauties are a fantastic hit at any dinner party.', 32.00, 'available');
INSERT INTO Fish (name, image_url, description, price, status) VALUES ('Sea Scallops', 'http://i.istockimg.com/file_thumbview_approve/58624176/5/stock-photo-58624176-scallops-on-black-stone-plate.jpg', 'Big, sweet and tender. True dry-pack scallops from the icey waters of Alaska. About 8-10 per pound', 16.84, 'available');
INSERT INTO Fish (name, image_url, description, price, status) VALUES ('Mahi Mahi88', 'http://i.istockimg.com/file_thumbview_approve/12556651/5/stock-photo-12556651-mahimahi.jpg', 'Lean flesh with a mild, sweet flavor profile, moderately firm texture and large, moist flakes.', 11.29, 'available');
INSERT INTO Fish (name, image_url, description, price, status) VALUES ('King Crab', 'http://i.istockimg.com/file_thumbview_approve/18294110/5/stock-photo-18294110-king-crab-legs.jpg', 'Crack these open and enjoy them plain or with one of our cocktail sauces', 42.34, 'available');
INSERT INTO Fish (name, image_url, description, price, status) VALUES ('Atlantic Salmon', 'http://i.istockimg.com/file_thumbview_approve/56241842/5/stock-photo-56241842-salmon-fish.jpg', 'This flaky, oily salmon is truly the king of the sea. Bake it, grill it, broil it...as good as it gets!', 14.53, 'available');
INSERT INTO Fish (name, image_url, description, price, status) VALUES ('Oysters', 'http://i.istockimg.com/file_thumbview_approve/58626682/5/stock-photo-58626682-fresh-oysters-on-a-black-stone-plate-top-view.jpg', 'A soft plump oyster with a sweet salty flavor and a clean finish.', 25.43, 'available');
INSERT INTO Fish (name, image_url, description, price, status) VALUES ('Mussels', 'http://i.istockimg.com/file_thumbview_approve/40450406/5/stock-photo-40450406-steamed-mussels.jpg', 'The best mussels from the Pacific Northwest with a full-flavored and complex taste.', 42.5, 'available');
INSERT INTO Fish (name, image_url, description, price, status) VALUES ('Jumbo Prawns', 'http://i.istockimg.com/file_thumbview_approve/67121439/5/stock-photo-67121439-fresh-tiger-shrimp-on-ice-on-a-black-stone-table.jpg', 'With 21-25 two bite prawns in each pound, these sweet morsels are perfect for shish-kabobs.', 22.50, 'available');

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