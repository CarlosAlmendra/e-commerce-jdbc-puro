CREATE TABLE IF NOT EXISTS products (
    uuid UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    uuid UUID PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS cart (
    uuid UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(uuid)
);

CREATE TABLE IF NOT EXISTS cart_items (
    uuid UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    product_id UUID NOT NULL,
    cart_id UUID NOT NULL,
    quantity NUMERIC NOT NULL,
    unit_price NUMERIC NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(uuid)
    FOREIGN KEY (product_id) REFERENCES products(uuid)
    FOREIGN KEY (cart_id) REFERENCES cart(uuid)
);

CREATE TABLE IF NOT EXISTS sale (
    uuid UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    cart_id UUID NOT NULL,
    total_value NUMERIC NOT NULL,
    payment_method TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(uuid),
    FOREIGN KEY (cart_id) REFERENCES cart(uuid)
);