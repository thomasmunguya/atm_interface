# ATM Interface Project 
INTRODUCTION
An automated teller machine (ATM) is computerized telecommunications device
that provides a financial institution's customers a secure method of performing financial
transactions, in a public space without the need for a human bank teller. Through ATM,
customers interact with a user-friendly interface that enables them to access their bank
accounts and perform various transactions.

DESCRIPTION
At the start, the user is provided with a log in screen and they are required
to enter their PIN NO. and Account details which are then verified by the machine.
In case of an unsuccessful attempt a user is asked again for their credentials but the
maximum number of attempt given to the user is limited to 3 only.

The user is then directed towards a main page
that displays a set of options/services along with their brief description, enabling
the user to understand their functioning. The user can select any of the listed
option and can continue with the transaction.
At any moment if the user wants to abort the transaction, he is
provided with an option to cancel it. Just by pressing the abort button they can
cancel all the changes made so far and can begin with a new transaction

After each transaction user has performed, a receipt is generated that contains all the
information about the transaction. The format of the generated receipt is as shown
below

PRODUCT FUNCTIONS
• Withdrawal/Deposit: The software allows the user to select the kind of
operation to be performed i.e. whether he wants to withdraw or deposit the
money.

• Amount: The amount to be withdrawn or deposited is then mentioned by
the user.

• Money Deposition : Money deposition shall be done with an envelope.
After typing the amount to be deposited and verification of the same, the
customer must insert the envelope in the depositary.

• Balance Transfer: Balance transfer shall be facilitated between any two
accounts linked to the card for example saving and checking account.

• Balance Enquiry: Balance enquiry for any account linked to the card shall
be facilitated.

• Billing: Any transaction shall be recorded in the form of a receipt and the
same would be dispensed to the customer. The billing procedures are
handled by the billing module that enable user to choose whether he wants
the printed statement of the transaction or just the updation in his account.

• Cancelling: The customer shall abort a transaction with the press of a
Cancel key. For example on entering a wrong depositing amount. In
addition the user can also cancel the entire session by pressing the abort
key and can start a fresh session all over again.

CONSTRAINTS
• The number of invalid pin entries attempted must not exceed three. After
three unsuccessful login attempts, the card is seized/blocked and need to
be unlocked by the bank.

• The simultaneous access to an account through both, withdrawing and depositing is
not supported.

• The minimum amount of money a user can withdraw is 0000 and the
maximum amount of money a user can withdraw in a session is 0000
and the maximum amount he can withdraw in a day is 0000

USER INTERFACE REQUIREMENTS
1. A login screen is provided in the beginning for entering the required
username/pin no. and account number.
2. An unsuccessful login leads to a reattempt (maximum three) screen for
again entering the same information. The successful login leads to a
screen displaying a list of supported languages from which a user can
select anyone.
3. After the login, a screen with a number of options is then shown to the
user. It contains all the options along with their brief description to
enable the user to understand their functioning and select the proper
option.
4. A screen will be provided for user to check his account balance.
5. A screen will be provided for the user to perform various transactions in
his account.
6. A printed statement is generated for the user displaying all the
transactions he performed.
