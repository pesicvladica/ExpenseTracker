import datetime
import random
from decimal import Decimal

def generate_transaction_sql(num_entries_per_type, user_id):
    sql_statements = []
    base_date = datetime.datetime(2025, 1, 1, 0, 0, 0)

    # Generate Transactions
    for transaction in ['INCOME', 'OUTCOME']:

        # Generate statements for transaction type
        for i in range(num_entries_per_type):

            # Define time_added
            days_since_start_of_year = (datetime.date(2025, 7, 28) - datetime.date(2025, 1, 1)).days
            random_days = random.randint(0, days_since_start_of_year)
            time_added = base_date + datetime.timedelta(days=random_days,
                                                         hours=random.randint(0, 23),
                                                         minutes=random.randint(0, 59),
                                                         seconds=random.randint(0, 59),
                                                         microseconds=random.randint(0, 999999))
            time_added_str = time_added.strftime('%Y-%m-%d %H:%M:%S.%f')[:-3]

            # Define amount
            amount = Decimal(str(round(random.uniform(50.00, 500.00), 2)))

            # Add statement
            sql_statements.append(
                f"INSERT INTO expense_tracker.transactions (user_id, amount, time_added, type) "
                f"VALUES ({user_id}, {amount}, '{time_added_str}', '{transaction}');"
            )

    return sql_statements

if __name__ == "__main__":
    num_entries = 1000
    user_id_to_use = 1

    sql_inserts = generate_transaction_sql(num_entries_per_type=num_entries, user_id=user_id_to_use)

    with open("transactions_data.sql", "w") as f:
        f.write("\n".join(sql_inserts))
