
INSERT INTO `ecommerce`.`customer` (`id`, `delivery_address`, `email`, `name`, `sure_name`) VALUES ('1', 'Italy', 'behnam263@gmail.com', 'behnam', 'Lotfi');
INSERT INTO `ecommerce`.`customer` (`id`, `delivery_address`, `email`, `name`, `sure_name`) VALUES ('2', 'Italy', 'behnam.lotfi@studenti.polito.it', 'behnam', 'behi');
INSERT INTO `ecommerce`.`wallet` (`id`, `amount`, `customer_id`) VALUES ('1', '1500', '1');
INSERT INTO `ecommerce`.`wallet` (`id`, `amount`, `customer_id`) VALUES ('2', '1200', '2');
INSERT INTO `ecommerce`.`transaction` (`id`, `amount_transfered`, `transaction_time`, `wallet_from`, `wallet_to`) VALUES ('1', '300', '2021-04-15 16:21:00', '1', '2');
INSERT INTO `ecommerce`.`transaction` (`id`, `amount_transfered`, `transaction_time`, `wallet_from`, `wallet_to`) VALUES ('2', '400', '2021-03-15 16:21:00', '2', '1');
INSERT INTO `ecommerce`.`transaction` (`id`, `amount_transfered`, `transaction_time`, `wallet_from`, `wallet_to`) VALUES ('3', '200', '2021-03-15 16:21:00', '1', '2');
INSERT INTO `ecommerce`.`transaction` (`id`, `amount_transfered`, `transaction_time`, `wallet_from`, `wallet_to`) VALUES ('4', '250', '2021-02-15 16:21:00', '2', '1');










