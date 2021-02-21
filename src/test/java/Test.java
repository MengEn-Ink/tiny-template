import cn.hutool.log.StaticLog;
import com.mysql.cj.xdevapi.ClientFactory;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


public class Test {


    public static void main(String[] args) throws IOException, CipherException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {

        Web3j web3j = Web3j.build(new HttpService("https://http-mainnet.hecochain.com"));  // defaults to http://localhost:8545/

        //检测连通
        String web3ClientVersion = web3j.web3ClientVersion().send().getWeb3ClientVersion();
        StaticLog.info("version=" + web3ClientVersion);

        //创建地址
//        String fileName = WalletUtils.generateLightNewWalletFile("Mora4739r#", new File("C:\\Wallet"));
//        System.out.println(fileName);
//        //分析地址
        String fileName="UTC--2021-02-20T16-16-22.576000000Z--01a1fc3b39f1398c02cecbee9453108c793d8f54.json";
        Credentials credentials = WalletUtils.loadCredentials("Mora4739r#","C:\\Wallet\\"+fileName );
        System.out.println(credentials.getAddress());
        System.out.println(credentials.getEcKeyPair().getPublicKey());
        System.out.println(credentials.getEcKeyPair().getPrivateKey().toString(16));
        String address = credentials.getAddress();
        //第二个参数：区块的参数，建议选最新区块
        EthGetBalance balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send();
        System.out.println(balance.getBalance().toString());
        //格式转化 wei-ether
        String blanceETH = Convert.fromWei(balance.getBalance().toString(), Convert.Unit.ETHER).toPlainString().concat(" ether");
        StaticLog.info(blanceETH);


    }
}
